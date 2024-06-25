package com.mahirsoft.webservice.Business.concretes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.DataAccess.AuthorizationRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleAuthorizationRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleRepository;
import com.mahirsoft.webservice.Entities.Exceptions.PermissionDeniedException;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Authorization;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Models.UserRole;
import com.mahirsoft.webservice.Entities.Models.UserRoleAuthorization;
import com.mahirsoft.webservice.Entities.Requests.PostCreateAuthorityRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUpdateAuthorityLevelsRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralAuthorizationResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralUserRoleResponse;
import com.mahirsoft.webservice.Entities.Response.GetUserRoleAndAuthorizationResponse;


@Service
public class AuthorityService {

    private UserRoleRepository userRoleRepository;

    private AuthorizationRepository authorizationRepository;

    private UserRoleAuthorizationRepository userRoleAuthorizationRepository;


    public AuthorityService(UserRoleRepository userRoleRepository, AuthorizationRepository authorizationRepository,
            UserRoleAuthorizationRepository userRoleAuthorizationRepository) {
        this.userRoleRepository = userRoleRepository;
        this.authorizationRepository = authorizationRepository;
        this.userRoleAuthorizationRepository = userRoleAuthorizationRepository;
    }


    public UserRole createAuthority(UserAuthentication user,PostCreateAuthorityRequest postCreateAuthorityRequest) {

        UserRole userRole = new UserRole();
        userRole.setName(postCreateAuthorityRequest.getName());
        userRole.setCompanyId(user.getCompanyId());


        return userRoleRepository.save(userRole);
    }


    public GetUserRoleAndAuthorizationResponse getRolesWithAuthority(UserAuthentication user) {

        var authorizations= authorizationRepository.findAll();

        var validAuths = companyValidAuthorizations(authorizations);

        var userRoles = user.getCompanyId().toGeneralUserRoleResponse();

        Map<Integer,List<Integer>> userRoleAuths = new HashMap<>();
        
        for(var eleman : user.getCompanyId().getUserRoles()){
            List<Integer> auths = new ArrayList<>();

            for(var authorization : eleman.getUserRoleAuthorizations()){
                auths.add(Long.valueOf(authorization.getAuthorizationId().getAuthorizationId()).intValue());
            }
            userRoleAuths.put(Long.valueOf(eleman.getUserRoleId()).intValue(), auths);
        }

        GetUserRoleAndAuthorizationResponse getUserRoleAndAuthorizationResponse = new GetUserRoleAndAuthorizationResponse();
        getUserRoleAndAuthorizationResponse.setAuthorizations(validAuths);
        getUserRoleAndAuthorizationResponse.setUserRoles(userRoles);
        getUserRoleAndAuthorizationResponse.setUserRoleAuths(userRoleAuths);


        return getUserRoleAndAuthorizationResponse;        
    }

    private List<GeneralAuthorizationResponse> companyValidAuthorizations(List<Authorization> authorizations){

        List<GeneralAuthorizationResponse> valids = new ArrayList<>();
        for(var eleman : authorizations){

            if(AuthorizationCodes.COMPANY_AUTHORIZATIONS.contains(Long.valueOf(eleman.getAuthorizationId()).intValue())){
                GeneralAuthorizationResponse generalAuthorizationResponse = new GeneralAuthorizationResponse();
                generalAuthorizationResponse.setAuthorizationId(eleman.getAuthorizationId());
                generalAuthorizationResponse.setDefinition(eleman.getDefinition());
                
                valids.add(generalAuthorizationResponse);
            }
        }

        return valids;
    }


    public UserRole updateUserRoleAuths(PostUpdateAuthorityLevelsRequest postUpdateAuthorityLevelsRequest,UserAuthentication user) {

        var userRoleAuthorizations = userRoleAuthorizationRepository.findByuserRoleId(user.getUserRoleId());
        List<UserRoleAuthorization> auths = new ArrayList<>();
        List<Authorization> authorizations = new ArrayList<>();
        List<Integer> authorizationIds = new ArrayList<>();
        
        if(postUpdateAuthorityLevelsRequest.getAuthorityNumbers().contains(AuthorizationCodes.GRANTING_PERMISSIONS) && postUpdateAuthorityLevelsRequest.getAuthorityNumbers().contains(AuthorizationCodes.GRANTING_OWN_PERMISSIONS)){
            postUpdateAuthorityLevelsRequest.getAuthorityNumbers().remove(postUpdateAuthorityLevelsRequest.getAuthorityNumbers().indexOf(AuthorizationCodes.GRANTING_OWN_PERMISSIONS));
        }

        boolean permissions = true;

        for(var eleman : userRoleAuthorizations){
            if(Long.valueOf(eleman.getAuthorizationId().getAuthorizationId()).intValue() == AuthorizationCodes.GRANTING_OWN_PERMISSIONS){
                permissions = false;
            }
            authorizations.add(eleman.getAuthorizationId());
            
        }

        var userRole = userRoleRepository.findById(postUpdateAuthorityLevelsRequest.getUserRoleId());
        var userRoleAuths = userRole.getUserRoleAuthorizations();

        for(var eleman : userRoleAuths){
            if(!postUpdateAuthorityLevelsRequest.getAuthorityNumbers().contains(Long.valueOf(eleman.getAuthorizationId().getAuthorizationId()).intValue())){
                userRoleAuthorizationRepository.deleteById(eleman.getUserRoleAuthorizationId());
            }
        }

        
        if(permissions){
            for(var eleman : postUpdateAuthorityLevelsRequest.getAuthorityNumbers()){
                if(AuthorizationCodes.COMPANY_AUTHORIZATIONS.contains(eleman) && !authorizationIds.contains(eleman)){
                    Authorization authorization = authorizationRepository.findById(Integer.valueOf(eleman).longValue()).orElseThrow(() -> new ResourceNotFoundException());
                    authorizationIds.add(eleman);
                    if(userRoleAuthorizationRepository.existsByAuthorizationIdAndUserRoleId(authorization,userRole)){
                        continue;
                    }
                    UserRoleAuthorization userRoleAuthorization = new UserRoleAuthorization();
                    userRoleAuthorization.setUserRoleId(userRole);
                    userRoleAuthorization.setAuthorizationId(authorization);

                    auths.add(userRoleAuthorizationRepository.save(userRoleAuthorization));
                }
                
            }
            
            userRole.setUserRoleAuthorizations(auths);
            return userRoleRepository.save(userRole);
        }



        for(var eleman : authorizations){
            if(postUpdateAuthorityLevelsRequest.getAuthorityNumbers().contains(Long.valueOf(eleman.getAuthorizationId()).intValue()) && !authorizationIds.contains(Long.valueOf(eleman.getAuthorizationId()).intValue())){
                authorizationIds.add(Long.valueOf(eleman.getAuthorizationId()).intValue());
                if(userRoleAuthorizationRepository.existsByAuthorizationIdAndUserRoleId(eleman,userRole)){
                    continue;
                }
                UserRoleAuthorization userRoleAuthorization = new UserRoleAuthorization();
                userRoleAuthorization.setUserRoleId(userRole);
                userRoleAuthorization.setAuthorizationId(eleman);

                auths.add(userRoleAuthorizationRepository.save(userRoleAuthorization));
            }

        }


        userRole.setUserRoleAuthorizations(auths);
        return userRoleRepository.save(userRole);
    }
    
}

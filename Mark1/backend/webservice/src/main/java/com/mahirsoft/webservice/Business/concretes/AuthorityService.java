package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.DataAccess.AuthorizationRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleAuthorizationRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Models.UserRole;
import com.mahirsoft.webservice.Entities.Requests.PostCreateAuthorityRequest;


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


    public void getRolesWtihAuthority(UserAuthentication user) {

        var auhorities = authorizationRepository.findByAuthorizationIdNot(AuthorizationCodes.SUPER_ADMIN);

        if(auhorities == null) throw new ResourceNotFoundException();
        
    }
    
}

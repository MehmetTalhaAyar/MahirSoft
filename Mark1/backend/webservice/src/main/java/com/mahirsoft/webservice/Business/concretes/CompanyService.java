package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.DataAccess.AuthorizationRepository;
import com.mahirsoft.webservice.DataAccess.CompanyCreateRequestRepository;
import com.mahirsoft.webservice.DataAccess.CompanyInvitationRepository;
import com.mahirsoft.webservice.DataAccess.CompanyRepository;
import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleAuthorizationRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleRepository;
import com.mahirsoft.webservice.Entities.Exceptions.PermissionDeniedException;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Exceptions.UserAlreadyInTheCompanyException;
import com.mahirsoft.webservice.Entities.Models.Authorization;
import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.CompanyCreateRequest;
import com.mahirsoft.webservice.Entities.Models.CompanyInvitation;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Models.UserRole;
import com.mahirsoft.webservice.Entities.Models.CompanyCreateRequest.CompanyCreateRequestCodes;
import com.mahirsoft.webservice.Entities.Models.CompanyInvitation.CompanyInvitationCodes;
import com.mahirsoft.webservice.Entities.Requests.PostAddUserToCompanyRequest;
import com.mahirsoft.webservice.Entities.Requests.PostCompanyCreateRequestReplyRequest;
import com.mahirsoft.webservice.Entities.Requests.PostCreateCompanyRequest;
import com.mahirsoft.webservice.Entities.Requests.PostGrantUserRoleRequest;
import com.mahirsoft.webservice.Entities.Requests.PostReplyToCompanyInvitationRequest;
import com.mahirsoft.webservice.Entities.Requests.PostSearchCompanyRolesRequest;

import jakarta.validation.Valid;

@Service
public class CompanyService {

    CompanyRepository companyRepository;

    CompanyInvitationRepository companyInvitationRepository;

    UserAuthenticationRepository userAuthenticationRepository;

    CompanyCreateRequestRepository companyCreateRequestRepository;

    UserRoleRepository userRoleRepository;

    UserRoleAuthorizationRepository userRoleAuthorizationRepository;

    AuthorizationRepository authorizationRepository;




    



   

    public CompanyService(CompanyRepository companyRepository, CompanyInvitationRepository companyInvitationRepository,
            UserAuthenticationRepository userAuthenticationRepository,
            CompanyCreateRequestRepository companyCreateRequestRepository, UserRoleRepository userRoleRepository,
            UserRoleAuthorizationRepository userRoleAuthorizationRepository,
            AuthorizationRepository authorizationRepository) {
        this.companyRepository = companyRepository;
        this.companyInvitationRepository = companyInvitationRepository;
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.companyCreateRequestRepository = companyCreateRequestRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRoleAuthorizationRepository = userRoleAuthorizationRepository;
        this.authorizationRepository = authorizationRepository;
    }


    public Company getCompany(long id){
        return companyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException()); 
    }
    

    public List<UserAuthentication> getCompanyMembers(long id){
        var company = companyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException());

        return company.getCompanyMembers();
    }


    public CompanyInvitation addANewMember(long companyId, PostAddUserToCompanyRequest postAddUserToCompanyRequest,
            UserAuthentication userWhoSentInvitation) {

        var userWhoReceiveInvitation = userAuthenticationRepository.findByEmail(postAddUserToCompanyRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException());    

        var company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException());

        if(userWhoReceiveInvitation.getCompanyId().getCompanyId() == company.getCompanyId()){
            throw new UserAlreadyInTheCompanyException();
        }

        CompanyInvitation companyInvitation = new CompanyInvitation();
        companyInvitation.setCompanyId(company);
        companyInvitation.setStatus(CompanyInvitationCodes.PENDING);
        companyInvitation.setUserWhoReceiveInvitation(userWhoReceiveInvitation);
        companyInvitation.setUserWhoSendInvitation(userWhoSentInvitation);

        return companyInvitationRepository.save(companyInvitation);
    }

    public List<CompanyInvitation> checkCompanyInvitations(UserAuthentication user ,int status) {
        return companyInvitationRepository.findByUserWhoReceiveInvitationAndStatus(user,status);
    }

    public Company replyCompanyInvitation(PostReplyToCompanyInvitationRequest postReplyToCompanyInvitationRequest,
            UserAuthentication user) {
        
        
        var company = companyRepository.findById(postReplyToCompanyInvitationRequest.getCompanyInfo().getId()).orElseThrow(()-> new ResourceNotFoundException());

        var companyInvitation = companyInvitationRepository.findByCompanyIdAndUserWhoReceiveInvitation(company, user).orElseThrow(()-> new ResourceNotFoundException());
                
        if(postReplyToCompanyInvitationRequest.getReply()){

            companyInvitation.setStatus(CompanyInvitationCodes.ACCEPTED);
        }
        else{
            companyInvitation.setStatus(CompanyInvitationCodes.REJECTED);
        } 

        companyInvitation = companyInvitationRepository.save(companyInvitation);

        return companyInvitation.getCompanyId();
    }

    public Company createCompany(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    public CompanyCreateRequest createCompanyCreationRequest(@Valid PostCreateCompanyRequest postCreateCompnayRequest,UserAuthentication user) {
        
        var companyRequest = companyCreateRequestRepository.findByStatusAndUserId(CompanyCreateRequestCodes.PENDING, user);

        if(companyRequest != null) return null; 

        CompanyCreateRequest companyCreateRequest = new CompanyCreateRequest();
        companyCreateRequest.setName(postCreateCompnayRequest.getName());
        companyCreateRequest.setDescription(postCreateCompnayRequest.getDescription());
        companyCreateRequest.setUserId(user);
        companyCreateRequest.setStatus(CompanyCreateRequestCodes.PENDING);
    
        return companyCreateRequestRepository.save(companyCreateRequest);
    }


    public List<CompanyCreateRequest> getAllCompanyCreateRequests() {
        return companyCreateRequestRepository.findByStatus(CompanyCreateRequestCodes.PENDING);
    }


    public CompanyCreateRequest createCompanyRequestResponse(@Valid PostCompanyCreateRequestReplyRequest postCompanyCreateRequestReplyRequest) {
        
        var companyRequest = companyCreateRequestRepository.findById(postCompanyCreateRequestReplyRequest.getRequestId()).orElseThrow(()-> new ResourceNotFoundException());

        if(!postCompanyCreateRequestReplyRequest.getIsOkey()){
            companyRequest.setStatus(CompanyCreateRequestCodes.REJECTED);

            return companyCreateRequestRepository.save(companyRequest);
        }

        Company newCompany = new Company();
        newCompany.setDescription(companyRequest.getDescription());
        newCompany.setName(companyRequest.getName());
        newCompany.setManagerId(companyRequest.getUserId());

        var company = companyRepository.save(newCompany);

        UserRole userRole = userRoleRepository.findById(3); // company administrator

        var user = companyRequest.getUserId();
        user.setUserRoleId(userRole);
        user.setCompanyId(company);
        user.setTitle(userRole.getName());

        userAuthenticationRepository.save(user);

        companyRequest.setStatus(CompanyCreateRequestCodes.ACCEPTED);

        return companyCreateRequestRepository.save(companyRequest);

    }


    public List<UserRole> getCompanyRoles(PostSearchCompanyRolesRequest postSearchCompanyRolesRequest, UserAuthentication user) {
        

        if(postSearchCompanyRolesRequest.getSearchKey().trim().isBlank()){
            return userRoleRepository.findFirst3ByCompanyIdAndName(user.getCompanyId(), postSearchCompanyRolesRequest.getSearchKey());

        }
        else{
            return userRoleRepository.findFirst3ByCompanyId(user.getCompanyId());
        }
    }


    public UserRole grantUserRole(@Valid PostGrantUserRoleRequest postGrantUserRoleRequest, UserAuthentication user) {

        Authorization authorization = authorizationRepository.findById(Integer.valueOf(AuthorizationCodes.GRANTING_PERMISSIONS).longValue()).orElseThrow(()-> new ResourceNotFoundException());

        boolean permission = userRoleAuthorizationRepository.existsByAuthorizationIdAndUserRoleId(authorization, user.getUserRoleId());
        
        if(permission){
            var userRole = userRoleRepository.findById(postGrantUserRoleRequest.getUserRoleId());

            if(userRole == null) return null;

            var updateUser = userAuthenticationRepository.findById(postGrantUserRoleRequest.getUserId());

            if(updateUser == null) return null;

            updateUser.setUserRoleId(userRole);

            userAuthenticationRepository.save(updateUser);

            return userRole;

        }else{
            if(postGrantUserRoleRequest.getUserRoleId() == user.getUserRoleId().getUserRoleId()){
                var updateUser = userAuthenticationRepository.findById(postGrantUserRoleRequest.getUserId());

                if(updateUser == null) return null;

                updateUser.setUserRoleId(user.getUserRoleId());

                userAuthenticationRepository.save(updateUser);

                return user.getUserRoleId();

            }
            else{
                throw new PermissionDeniedException();
            }

        }

        
    }
}

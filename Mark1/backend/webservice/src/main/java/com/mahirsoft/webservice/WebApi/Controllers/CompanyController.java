package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.mahirsoft.webservice.Business.concretes.CompanyService;
import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.UserAuthenticationService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.ResponseMessage;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.CompanyInvitation;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Models.CompanyInvitation.CompanyInvitationCodes;
import com.mahirsoft.webservice.Entities.Requests.CreateCompanyMemberRequest;
import com.mahirsoft.webservice.Entities.Requests.CreateCompnayRequest;
import com.mahirsoft.webservice.Entities.Requests.PostAddUserToCompanyRequest;
import com.mahirsoft.webservice.Entities.Requests.PostReplyToCompanyInvitationRequest;
import com.mahirsoft.webservice.Entities.Requests.PostSearchCompanyMembersRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralCompanyResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {

    private CompanyService companyService;

    private PermissionService permissionService;

    private UserAuthenticationService userAuthenticationService;

    public CompanyController(CompanyService companyService, PermissionService permissionService,
            UserAuthenticationService userAuthenticationService) {
        this.companyService = companyService;
        this.permissionService = permissionService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/create")
    public ResponseEntity<?>  createCompany(@Valid @RequestBody CreateCompnayRequest createCompnayRequest,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isTherePermission(currentUser, AuthorizationCodes.CREATE_COMPANY); 

        var manager = userAuthenticationService.findById(createCompnayRequest.getManagerId());
        
        if(manager == null) throw new UserNotFoundException();

        Company newCompany = new Company();

        newCompany.setName(createCompnayRequest.getName());
        newCompany.setManagerId(manager);
        newCompany.setDescription(createCompnayRequest.getDescription());

        var company = companyService.createCompany(newCompany);
        if(company == null) return new ResponseEntity<String>("Something went wrong!", HttpStatusCode.valueOf(404));
        manager.setCompanyId(newCompany);
        var updateduser = userAuthenticationService.updateUserWithCompany(manager);
        if(updateduser == null) return new ResponseEntity<String>("Something went wrong!", HttpStatusCode.valueOf(404));

        GeneralCompanyResponse companyResponse = new GeneralCompanyResponse();
        companyResponse.setDescription(company.getDescription());
        companyResponse.setId(company.getCompanyId());
        companyResponse.setName(company.getName());
        
        GeneralUserAuthenticationResponse generaluser = updateduser.toGeneralUserAuthenticationResponse();
        generaluser.setCompany(company.toCompanyResponse());
        
        companyResponse.setManager(generaluser);


        return new ResponseEntity<GeneralCompanyResponse>(companyResponse, HttpStatusCode.valueOf(201));
    }

    @PostMapping("/add/employee/{companyId}")
    public ResponseEntity<?> addEmployeeToCompany(@PathVariable long companyId,@RequestBody PostAddUserToCompanyRequest postAddUserToCompanyRequest,@AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.INVITATION_TO_THE_COMPANY);

        var companyInvitation = companyService.addANewMember(companyId,postAddUserToCompanyRequest,user);

        if(companyInvitation == null) return new ResponseEntity<String>("Something went wrong!",  HttpStatusCode.valueOf(404));

        return new ResponseEntity<ResponseMessage>( new ResponseMessage("User Invitation Sent."),  HttpStatusCode.valueOf(200));
    }

    @PostMapping("/reply/invitation")
    public ResponseEntity<?> handleReplyToCompanyInvitations(@Valid @RequestBody PostReplyToCompanyInvitationRequest postReplyToCompanyInvitationRequest,  @AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.ANY_AUTHORIZATION);

        companyService.replyCompanyInvitation(postReplyToCompanyInvitationRequest,user);
        
        
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Message received."),HttpStatus.OK);
    }

    @GetMapping("/check/invitations") // burada reject ve accepted durumu için birşeyler düşünülecek
    public ResponseEntity<?> handleCheckCompanyInvitations(@AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.ANY_AUTHORIZATION);

        List<CompanyInvitation> invitations = companyService.checkCompanyInvitations(user,CompanyInvitationCodes.PENDING);

        List<GeneralCompanyResponse> generalCompanyResponses = new ArrayList<>();

        for(var invitation : invitations){
            GeneralCompanyResponse generalCompanyResponse = invitation.getCompanyId().toGeneralCompanyResponse();
            generalCompanyResponses.add(generalCompanyResponse);
        }

        return new ResponseEntity<List<GeneralCompanyResponse>>(generalCompanyResponses,HttpStatus.OK);
    }



    @PostMapping("/members") 
    public List<GeneralUserAuthenticationResponse> getcompanyMembers(@RequestBody PostSearchCompanyMembersRequest postSearchCompanyMembersRequest,@AuthenticationPrincipal DefaultUser currentUser){

        var chosenUser = permissionService.isTherePermission(currentUser, AuthorizationCodes.ANY_AUTHORIZATION);

        if(chosenUser.getCompanyId() == null){
            List<GeneralUserAuthenticationResponse> onlyHimself = new ArrayList<>();
            onlyHimself.add(chosenUser.toGeneralUserAuthenticationResponse());
            return onlyHimself;
        }
        Company company = companyService.getCompany(chosenUser.getCompanyId().getCompanyId());

        var users = userAuthenticationService.getUsersBycompany(company, postSearchCompanyMembersRequest);

        List<GeneralUserAuthenticationResponse> generalUsers = new ArrayList<>();
        for(var user : users){
            generalUsers.add(user.toGeneralUserAuthenticationResponse());
        }

        return generalUsers;

    }


    @GetMapping("/projects")
    public List<GeneralProjectResponse> getCompanyProjects(@AuthenticationPrincipal DefaultUser user){
        var currentUser = permissionService.isTherePermission(user, AuthorizationCodes.ANY_AUTHORIZATION);

        if(currentUser.getCompanyId() != null){
            if(currentUser.getCompanyId().getManagerId().getUserId() == currentUser.getUserId()){

                return currentUser.getCompanyId().toGeneralProjectResponses();
            }
            else {
                return currentUser.toGeneralProjectResponses();
            }
        }
        else {
            return currentUser.toGeneralProjectResponses();
        }
        

    }

    @PostMapping("/createcompanymember")
    public ResponseEntity<?> createCompanyMember(@Valid @RequestBody CreateCompanyMemberRequest CreateCompanyMemberRequest ,@AuthenticationPrincipal DefaultUser user){

        var createdUser = permissionService.isTherePermission(user, AuthorizationCodes.CREATING_A_REGISTERED_USER_FOR_THE_COMPANY);

        UserAuthentication newMember = CreateCompanyMemberRequest.toUserAuthentication();
        newMember.setCreatedById(createdUser);
        newMember.setCompanyId(createdUser.getCompanyId());

        var newCreatedUser = userAuthenticationService.addUserToCompany(newMember);

        GeneralUserAuthenticationResponse generalUser = newCreatedUser.toGeneralUserAuthenticationResponse();

        return new ResponseEntity<GeneralUserAuthenticationResponse>(generalUser,HttpStatusCode.valueOf(201));
    }


    
}

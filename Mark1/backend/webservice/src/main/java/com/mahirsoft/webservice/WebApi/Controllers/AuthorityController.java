package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.AuthorityService;
import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.ResponseMessage;
import com.mahirsoft.webservice.Entities.Requests.PostCreateAuthorityRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUpdateAuthorityLevelsRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralUserRoleResponse;
import com.mahirsoft.webservice.Entities.Response.GetUserRoleAndAuthorizationResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/authority")
public class AuthorityController {

    private PermissionService permissionService;

    private AuthorityService authorityService;

    public AuthorityController(PermissionService permissionService, AuthorityService authorityService) {
        this.permissionService = permissionService;
        this.authorityService = authorityService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAuthority(@Valid @RequestBody PostCreateAuthorityRequest postCreateAuthorityRequest,@AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isThereAnyOfThesePermissions(currentUser, List.of(AuthorizationCodes.GRANTING_OWN_PERMISSIONS,AuthorizationCodes.GRANTING_PERMISSIONS));

        var userRole = authorityService.createAuthority(user,postCreateAuthorityRequest);

        GeneralUserRoleResponse generalUserRoleResponse = userRole.toGeneralUserRoleResponse();

        return new ResponseEntity<GeneralUserRoleResponse>(generalUserRoleResponse,HttpStatusCode.valueOf(201));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateAuthority(@Valid @RequestBody PostUpdateAuthorityLevelsRequest postUpdateAuthorityLevelsRequest, @AuthenticationPrincipal DefaultUser currentUser ){

        var user = permissionService.isThereAnyOfThesePermissions(currentUser, List.of(AuthorizationCodes.GRANTING_OWN_PERMISSIONS,AuthorizationCodes.GRANTING_PERMISSIONS));

        authorityService.updateUserRoleAuths(postUpdateAuthorityLevelsRequest,user);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("oldu."),HttpStatusCode.valueOf(200));
    }

    @GetMapping("/role")
    public ResponseEntity<?> getRolesAndAuthority(@AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.ANY_AUTHORIZATION);

        var response = authorityService.getRolesWithAuthority(user);

    
        return new ResponseEntity<GetUserRoleAndAuthorizationResponse>(response,HttpStatus.OK);
    }



    
}

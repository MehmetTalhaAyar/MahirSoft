package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.ProjectAndUserService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.PostProjectAndUserResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projectuser")
public class ProjectAndUserController {

    ProjectAndUserService projectAndUserService;

    PermissionService permissionService;

    

    
    public ProjectAndUserController(ProjectAndUserService projectAndUserService, PermissionService permissionService) {
        this.projectAndUserService = projectAndUserService;
        this.permissionService = permissionService;
    }


    // burası sadece yazılım tarafında kullanılıyor.
    @PostMapping("/{id}")
    public ResponseEntity<?> createProjectWithLead(@PathVariable long id,@Valid @RequestBody CreateProjectRequest createProjectRequest,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN);
        
        var response = projectAndUserService.createProjectWithLeading(id, createProjectRequest);

        if(response == null){
            return null;
        }

        GeneralUserAuthenticationResponse leadPerson = response.toLeadPerson();
        
        PostProjectAndUserResponse postProjectAndUserResponse = new PostProjectAndUserResponse();
        postProjectAndUserResponse.setCreatedOn(response.getCreatedOn());
        postProjectAndUserResponse.setName(response.getName());
        postProjectAndUserResponse.setStages(response.getStages());
        postProjectAndUserResponse.setLeadingPerson(leadPerson);

        return new ResponseEntity<PostProjectAndUserResponse>(postProjectAndUserResponse,HttpStatusCode.valueOf(200));


    }


    @DeleteMapping("/{id}") 
    public ResponseEntity<?> deleteProject(@PathVariable long id,@AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.PROJECT_DELETE);
        
        var project =  projectAndUserService.softDeleteProject(id,user);
        if(project == null) return new ResponseEntity<String>("Only Leader delete project", HttpStatusCode.valueOf(400));
         
        return new ResponseEntity<String>("Project Deleted", HttpStatusCode.valueOf(200));
    }
}

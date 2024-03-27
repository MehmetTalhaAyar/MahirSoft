package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.ProjectService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.GetProjectResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {

    ProjectService projectService;

    PermissionService permissionService;


    

    public ProjectController(ProjectService projectService, PermissionService permissionService) {
        this.projectService = projectService;
        this.permissionService = permissionService;
    }

    @GetMapping("/{id}") // test için kullanılıyor
    public ResponseEntity<?> getProjectById(@PathVariable long id,@AuthenticationPrincipal DefaultUser currentUser){
        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN);
        
        String body = "Project Not Found";
        var project = projectService.getProject(id);


        if(project == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(400));

        }

        GeneralUserAuthenticationResponse leadPerson = new GeneralUserAuthenticationResponse();
        leadPerson.setEmail(project.getLeadingPersonId().getEmail());
        leadPerson.setGsm(project.getLeadingPersonId().getGsm());
        leadPerson.setName(project.getLeadingPersonId().getName());
        leadPerson.setSurname(project.getLeadingPersonId().getSurname());
        leadPerson.setUserId(project.getLeadingPersonId().getUserId());
        
        GetProjectResponse getProjectResponse = new GetProjectResponse();
        getProjectResponse.setCreatedOn(project.getCreatedOn());
        getProjectResponse.setLeadingPerson(leadPerson);
        getProjectResponse.setName(project.getName());
        getProjectResponse.setStages(project.getStages());


        return new ResponseEntity<GetProjectResponse>(getProjectResponse, HttpStatusCode.valueOf(200));
        

    }

    @PostMapping("/add") 
    public ResponseEntity<?> addProject(@Valid @RequestBody CreateProjectRequest createProjectRequest,@AuthenticationPrincipal DefaultUser currentUser){
        
        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN);

        String body = "Project created";

        projectService.createProject(createProjectRequest);

        return new ResponseEntity<String>(body,HttpStatusCode.valueOf(201));
    }

    

}

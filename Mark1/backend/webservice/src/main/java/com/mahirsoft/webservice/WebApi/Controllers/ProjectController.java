package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.ProjectService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.ResponseMessage;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Requests.DeleteAMemberFromTheProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PostAddMemberToProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PostCreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PostSearchProjectMembersRequest;
import com.mahirsoft.webservice.Entities.Requests.PutProjectDescriptionRequest;
import com.mahirsoft.webservice.Entities.Requests.PutProjectNameRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralUserResponse;
import com.mahirsoft.webservice.Entities.Response.GetProjectDetailsResponse;
import com.mahirsoft.webservice.Entities.Response.GetProjectStageDetailsResponse;
import com.mahirsoft.webservice.Entities.Response.PostProjectAndStageResponse;
import com.mahirsoft.webservice.Entities.Response.PostProjectAndUserResponse;
import com.mahirsoft.webservice.Entities.Response.GetProjectResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private ProjectService projectService;

    private PermissionService permissionService;


    

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

        GeneralUserResponse leadPerson = new GeneralUserResponse();
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


    @GetMapping("/details/stage/{projectId}") 
    public ResponseEntity<?> stageDetails(@PathVariable long projectId,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isInThisProject(currentUser, projectId, AuthorizationCodes.ANY_AUTHORIZATION);

        var projectDetails = projectService.getProject(projectId);

        if(projectDetails == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400));

        GetProjectStageDetailsResponse getProjectDetailsResponse = new GetProjectStageDetailsResponse();
        getProjectDetailsResponse.setStages(projectDetails.toGeneralStageResponses());

        return new ResponseEntity<GetProjectStageDetailsResponse>(getProjectDetailsResponse,HttpStatusCode.valueOf(200));
    }
    

    @GetMapping("/details/project/{projectId}")
    public ResponseEntity<?> projectDetails(@PathVariable long projectId,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isInThisProject(currentUser, projectId, AuthorizationCodes.ANY_AUTHORIZATION);

        var project = projectService.getProject(projectId);

        if(project == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400));

        GetProjectDetailsResponse projectDetailsResponse = new GetProjectDetailsResponse();
        projectDetailsResponse.setName(project.getName());
        projectDetailsResponse.setDescription(project.getDescription());
        projectDetailsResponse.setProjectLead(project.getLeadingPersonId().toGeneralUserAuthenticationResponse());
        projectDetailsResponse.setProjectMembers(project.toGeneralUserAuthenticationResponses());
        projectDetailsResponse.setProjectStages(project.toGeneralStageResponses());
        projectDetailsResponse.setTaskCounts(projectService.getTaskCounts(project));

        return new ResponseEntity<GetProjectDetailsResponse>(projectDetailsResponse,HttpStatusCode.valueOf(200));
    }

    @PutMapping("/description/{projectId}")
    public ResponseEntity<?> updateProjectDescription(@PathVariable long projectId,@Valid @RequestBody PutProjectDescriptionRequest projectDescriptionRequest,@AuthenticationPrincipal DefaultUser currentUser){
        permissionService.isInThisProject(currentUser, projectId, AuthorizationCodes.PROJECT_UPDATE);

        var project = projectService.updateDescription(projectId,projectDescriptionRequest);

        if(project == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400));

        GeneralProjectResponse generalProjectResponse = project.toGeneralProjectResponse();

        return new ResponseEntity<>(generalProjectResponse,HttpStatusCode.valueOf(200));
    }

    @PutMapping("/name/{projectId}")
    public ResponseEntity<?> updateProjectName(@PathVariable long projectId,@Valid @RequestBody PutProjectNameRequest projectNameRequest,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isInThisProject(currentUser, projectId, AuthorizationCodes.PROJECT_UPDATE);

        var project = projectService.updateName(projectId,projectNameRequest);

        if(project == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400));

        GeneralProjectResponse projectResponse = project.toGeneralProjectResponse();

        return new ResponseEntity<GeneralProjectResponse>(projectResponse,HttpStatusCode.valueOf(200));

    }

    @PostMapping("/add/employee")
    public ResponseEntity<?> addNewMember(@RequestBody PostAddMemberToProjectRequest postAddMemberToProjectRequest, @AuthenticationPrincipal DefaultUser currentUser ){

        var user = permissionService.isTherePermission(currentUser , AuthorizationCodes.ADDING_SOMEONE_TO_THE_PROJECT);

        var projectUser = projectService.addANewMember(postAddMemberToProjectRequest,user);

        if(projectUser == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        GeneralProjectResponse generalProjectResponse = projectUser.getProjectId().toGeneralProjectResponse();

        return new ResponseEntity<GeneralProjectResponse>(generalProjectResponse,HttpStatus.OK);
    }

    @PostMapping("/members")
    public ResponseEntity<?> handleGetProjectMembersBySearchKey( @Valid @RequestBody PostSearchProjectMembersRequest postSearchProjectMembersRequest, @AuthenticationPrincipal DefaultUser currentUser ){

        var user = permissionService.isInThisProject(currentUser, postSearchProjectMembersRequest.getProjectId(), AuthorizationCodes.ADDING_SOMEONE_TO_THE_PROJECT);

        var users = projectService.getMembersWhoNotInThisProject(postSearchProjectMembersRequest,user);

        List<GeneralUserResponse> generalUsers = new ArrayList<>();
    
        for(var eleman : users){

            GeneralUserResponse generalUserAuthenticationResponse = eleman.toGeneralUserAuthenticationResponse();
            generalUsers.add(generalUserAuthenticationResponse);
        }


        return new ResponseEntity<List<GeneralUserResponse>>(generalUsers,HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> handleDeleteAMemberFromTheProject(@Valid @RequestBody DeleteAMemberFromTheProjectRequest deleteAMemberFromTheProjectRequest, @AuthenticationPrincipal DefaultUser currentUser ){

        var user = permissionService.isInThisProject(currentUser, deleteAMemberFromTheProjectRequest.getProjectId(), AuthorizationCodes.ADDING_SOMEONE_TO_THE_PROJECT);

        var projectResponse = projectService.deleteMemberFromProject(deleteAMemberFromTheProjectRequest,user);

        if(projectResponse == null) return new ResponseEntity<ResponseMessage>(new ResponseMessage("Something went wrong!"),HttpStatus.BAD_REQUEST);



        return new ResponseEntity<GeneralProjectResponse>(projectResponse,HttpStatus.OK);
    }

    @PostMapping("/addstage/{id}")
    public ResponseEntity<?> addStage(@PathVariable long id,@Valid @RequestBody CreateStageRequest createStageRequest,@AuthenticationPrincipal DefaultUser currentUser){
        
        
        String body = "Project Not Found";

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.STAGE_CREATE);

        
        var response = projectService.addStageToProject(id, createStageRequest,user);
        if(response == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(400));
        }

        GeneralProjectResponse generalProjectResponse = response.getProjectId().toGeneralProjectResponse();

        PostProjectAndStageResponse postProjectAndStageResponse = new PostProjectAndStageResponse();
        postProjectAndStageResponse.setName(response.getName());
        postProjectAndStageResponse.setProject(generalProjectResponse);

        return new ResponseEntity<PostProjectAndStageResponse>(postProjectAndStageResponse, HttpStatusCode.valueOf(200));

    }

    @PostMapping("/defaultProject") // test için sadece super admin istek atabilir
    public ResponseEntity<?> addDefaultProject(@Valid @RequestBody CreateProjectRequest createProjectRequest,@AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser,AuthorizationCodes.SUPER_ADMIN);

        var project = projectService.createDefaultProject(createProjectRequest,createProjectRequest.getLeadPersonId(), user);

        GeneralProjectResponse generalProjectResponse = new GeneralProjectResponse();

        generalProjectResponse.setCreatedOn(project.getCreatedOn());
        generalProjectResponse.setLeadingPerson(project.toLeadPerson());
        generalProjectResponse.setName(project.getName());
        generalProjectResponse.setStages(project.toGeneralStageResponses());


        return new ResponseEntity<GeneralProjectResponse>(generalProjectResponse, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/createproject")
    public ResponseEntity<?> addProject(@Valid @RequestBody PostCreateProjectRequest postCreateProjectRequest ,@AuthenticationPrincipal DefaultUser currentUser){


        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.PROJECT_CREATE); // proje oluşturma yetkisi

        var project = projectService.createProject(postCreateProjectRequest, user);

        if(project == null) return new ResponseEntity<String>("Something Went Wrong!", HttpStatusCode.valueOf(400));

        GeneralProjectResponse generalProjectResponse = project.toGeneralProjectResponse();
        
        return new ResponseEntity<GeneralProjectResponse>(generalProjectResponse, HttpStatusCode.valueOf(201));
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> createProjectWithLead(@PathVariable long id,@Valid @RequestBody CreateProjectRequest createProjectRequest,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN);
        
        var response = projectService.createProjectWithLeading(id, createProjectRequest);

        if(response == null){
            return null;
        }

        GeneralUserResponse leadPerson = response.toLeadPerson();
        
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
        
        var project =  projectService.softDeleteProject(id,user);
        
        if(project == null) return new ResponseEntity<String>("Only Leader delete project", HttpStatusCode.valueOf(400));
         
        return new ResponseEntity<String>("Project Deleted", HttpStatusCode.valueOf(200));
    }



}

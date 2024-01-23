package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.ProjectAndStageService;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Requests.PostCreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.PostProjectAndStageResponse;
import com.mahirsoft.webservice.Entities.Response.ProjectMembersAndStageResponse;
import com.mahirsoft.webservice.Entities.Response.UpdateTaskResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projectandstage")
public class ProjectAndStageController {

    ProjectAndStageService projectAndStageService;
    

    public ProjectAndStageController(ProjectAndStageService projectAndStageService) {
        this.projectAndStageService = projectAndStageService;
    }



    @PostMapping("/{id}")
    public ResponseEntity<?> addStage(@PathVariable long id,@Valid @RequestBody CreateStageRequest createStageRequest){
        String body = "Project Not Found";

        var response = projectAndStageService.addStageToProject(id, createStageRequest);
        if(response == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(400));
        }

        GeneralProjectResponse generalProjectResponse = response.getProjectId().toGeneralProjectResponse();

        PostProjectAndStageResponse postProjectAndStageResponse = new PostProjectAndStageResponse();
        postProjectAndStageResponse.setName(response.getName());
        postProjectAndStageResponse.setProject(generalProjectResponse);

        return new ResponseEntity<PostProjectAndStageResponse>(postProjectAndStageResponse, HttpStatusCode.valueOf(200));

    }

    @PostMapping("/defaultProject")
    public ResponseEntity<?> addDefaultProject(@Valid @RequestBody CreateProjectRequest createProjectRequest,@AuthenticationPrincipal DefaultUser user){
        var project = projectAndStageService.createDefaultProject(createProjectRequest, user.getId());

        GeneralProjectResponse generalProjectResponse = new GeneralProjectResponse();

        generalProjectResponse.setCreatedOn(project.getCreatedOn());
        generalProjectResponse.setLeadingPerson(project.toLeadPerson());
        generalProjectResponse.setName(project.getName());
        generalProjectResponse.setStages(project.toGeneralStageResponse());


        return new ResponseEntity<GeneralProjectResponse>(generalProjectResponse, HttpStatusCode.valueOf(200));
    }


    @PostMapping("/createproject")
    public ResponseEntity<?> addProject(@Valid @RequestBody PostCreateProjectRequest postCreateProjectRequest ,@AuthenticationPrincipal DefaultUser currentUser){

        var project = projectAndStageService.createProject(postCreateProjectRequest, currentUser.getId());

        if(project == null) return new ResponseEntity<String>("Something Went Wrong!", HttpStatusCode.valueOf(400));

        GeneralProjectResponse generalProjectResponse = project.toGeneralProjectResponse();
        
        return new ResponseEntity<GeneralProjectResponse>(generalProjectResponse, HttpStatusCode.valueOf(201));
    }


    @GetMapping("/getallmembersandstage/{stageId}")
    public ResponseEntity<?> getAllMembersAndStage(@PathVariable long stageId){

        var projectMemberAndStages = projectAndStageService.getProjectMembersAndStageByStageId(stageId);

        if(projectMemberAndStages == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400));

        return new ResponseEntity<ProjectMembersAndStageResponse>(projectMemberAndStages, HttpStatusCode.valueOf(200));

    }

    @PutMapping("/updatetask/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable long taskId,@RequestBody UpdateTaskRequest updateTaskRequest){

        String body = "Task not Found";
        var updatedTask = projectAndStageService.updateTask(taskId, updateTaskRequest);
        if(updatedTask == null){
            return new ResponseEntity<String>(body , HttpStatusCode.valueOf(400));
        }


        // response nesnesine map işlemi
        UpdateTaskResponse updateTaskResponse = new UpdateTaskResponse();
        updateTaskResponse.setEndTime(updatedTask.getTaskDeadlineDate());
        updateTaskResponse.setId(updatedTask.getTaskId());
        updateTaskResponse.setReporterUser(updatedTask.getReportsToId().toGeneralUserAuthenticationResponse());
        updateTaskResponse.setResponsibleId(updatedTask.getResposibleId().toGeneralUserAuthenticationResponse());

        return new ResponseEntity<UpdateTaskResponse>(updateTaskResponse, HttpStatusCode.valueOf(200));
    }


    
}

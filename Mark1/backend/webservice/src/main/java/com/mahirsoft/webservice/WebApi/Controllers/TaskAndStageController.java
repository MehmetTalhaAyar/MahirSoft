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

import com.mahirsoft.webservice.Business.concretes.StageService;
import com.mahirsoft.webservice.Business.concretes.TaskService;
import com.mahirsoft.webservice.Business.concretes.UserAuthenticationService;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralStageResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralTaskResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/taskstage")
public class TaskAndStageController {
    
    TaskService taskService;

    StageService stageService;

    UserAuthenticationService userAuthenticationService;

    public TaskAndStageController(TaskService taskService, StageService stageService,UserAuthenticationService userAuthenticationService) {
        this.taskService = taskService;
        this.stageService = stageService;
        this.userAuthenticationService = userAuthenticationService;
    }


    @PostMapping("/{stageId}")
    public ResponseEntity<?> addTaskToStage(@PathVariable long stageId,@Valid @RequestBody CreateTaskRequest createTaskRequest,@AuthenticationPrincipal DefaultUser user){
        
        var currentUser = userAuthenticationService.findById(user.getId());

        if(currentUser == null) return null;

        var stage = stageService.getStage(stageId);

        if (stage == null) return null;

        Task task = new Task();
        
        task.setCreatedById(currentUser);
        task.setStageId(stage);
        task.setResposibleId(currentUser);
        task.setTaskName(createTaskRequest.getTaskName());
        task.setTaskDescription(createTaskRequest.getTaskDescription());

        taskService.createTask(task);

        GeneralTaskResponse generalTask = new GeneralTaskResponse();
        generalTask.setDescription(task.getTaskDescription());
        generalTask.setName(task.getTaskName());
        generalTask.setResponsiblePerson(currentUser.toGeneralUserAuthenticationResponse());
        generalTask.setStage(stage.toGeneralStageResponse());


        return new ResponseEntity<GeneralTaskResponse>(generalTask, HttpStatusCode.valueOf(201));
    }


    @GetMapping("/alltasks/{stageId}")
    public GeneralStageResponse getAllTaskByStage(@PathVariable long stageId){
        var stage = stageService.getStage(stageId);

        if(stage == null ) return null;
        
        return stage.toGeneralStageResponse();
    }

    



}

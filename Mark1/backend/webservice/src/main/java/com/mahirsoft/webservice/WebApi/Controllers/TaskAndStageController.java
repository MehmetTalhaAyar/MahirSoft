package com.mahirsoft.webservice.WebApi.Controllers;


import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.StageService;
import com.mahirsoft.webservice.Business.concretes.TaskService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
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

    PermissionService permissionService;

   

    public TaskAndStageController(TaskService taskService, StageService stageService,
            PermissionService permissionService) {
        this.taskService = taskService;
        this.stageService = stageService;
        this.permissionService = permissionService;
    }


    @PostMapping("/{stageId}")
    public ResponseEntity<?> addTaskToStage(@PathVariable long stageId,@Valid @RequestBody CreateTaskRequest createTaskRequest,@AuthenticationPrincipal DefaultUser currentUser){
        
        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.TASK_CREATE);


        var stage = stageService.getStage(stageId);

        if (stage == null) return null;

        Task task = new Task();
        
        task.setCreatedById(user);
        task.setReportsToId(user);
        task.setStageId(stage);
        task.setResposibleId(user);
        task.setTaskName(createTaskRequest.getTaskName());
        task.setTaskDescription(createTaskRequest.getTaskDescription());

        var createdTask = taskService.createTask(task);
        if(createdTask == null) return null;

        GeneralTaskResponse generalTask = new GeneralTaskResponse();
        generalTask.setId(createdTask.getTaskId());
        generalTask.setDescription(createdTask.getTaskDescription());
        generalTask.setName(createdTask.getTaskName());
        generalTask.setResponsiblePerson(user.toGeneralUserAuthenticationResponse());
        generalTask.setStage(stage.toGeneralStageResponse());


        return new ResponseEntity<GeneralTaskResponse>(generalTask, HttpStatusCode.valueOf(201));
    }


    @GetMapping("/alltasks/{stageId}")// burada istek atan kişinin projede olması kontrol edilecek
    public GeneralStageResponse getAllTaskByStage(@PathVariable long stageId,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isInThisProjectFindByStageId(currentUser, stageId);

        var stage = stageService.getStage(stageId);

        if(stage == null ) return null;
        
        return stage.toGeneralStageResponse();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id,@AuthenticationPrincipal DefaultUser currentUser){
        String body = "Task Not Found";

        permissionService.isTherePermission(currentUser, AuthorizationCodes.TASK_DELETE);

        var task = taskService.findById(id);

        var message = taskService.softDeleteTask(task);

        if(message == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));

    }


    

    



}

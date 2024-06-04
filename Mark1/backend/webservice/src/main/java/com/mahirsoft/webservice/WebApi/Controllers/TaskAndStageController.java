package com.mahirsoft.webservice.WebApi.Controllers;


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
import com.mahirsoft.webservice.Business.concretes.StageService;
import com.mahirsoft.webservice.Business.concretes.TaskAndStageService;
import com.mahirsoft.webservice.Business.concretes.TaskService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskStageRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralStageResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralTaskResponse;
import com.mahirsoft.webservice.Entities.Response.TaskResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/taskstage")
public class TaskAndStageController {
    
    TaskService taskService;

    StageService stageService;

    PermissionService permissionService;
    
    TaskAndStageService taskAndStageService;



    public TaskAndStageController(TaskService taskService, StageService stageService,
            PermissionService permissionService, TaskAndStageService taskAndStageService) {
        this.taskService = taskService;
        this.stageService = stageService;
        this.permissionService = permissionService;
        this.taskAndStageService = taskAndStageService;
    }


    @PostMapping("/{stageId}")
    public ResponseEntity<?> addTaskToStage(@PathVariable long stageId,@Valid @RequestBody CreateTaskRequest createTaskRequest,@AuthenticationPrincipal DefaultUser currentUser){
        
        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.TASK_CREATE);


        var stage = stageService.getStage(stageId);

        if (stage == null) return null;

        // farklı bir şirketten birinin task yetkisi ile farklı bir şirketin stage ine task eklemesi engelleniyor.
        permissionService.isInThisProjectFindByStageId(currentUser, stageId,AuthorizationCodes.TASK_CREATE); 

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


    @GetMapping("/alltasks/{stageId}")
    public GeneralStageResponse getAllTaskByStage(@PathVariable long stageId,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isInThisProjectFindByStageId(currentUser, stageId,AuthorizationCodes.ANY_AUTHORIZATION);

        var stage = stageService.getStage(stageId);

        if(stage == null ) return null;
        
        return stage.toGeneralStageResponse();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id,@AuthenticationPrincipal DefaultUser currentUser){
        String body = "Task Not Found";

        var task = taskService.findById(id);

        if(task == null) return null;

        permissionService.isInThisProjectFindByTaskId(currentUser, task.getTaskId(),AuthorizationCodes.TASK_DELETE);

    
        var message = taskService.softDeleteTask(task);

        if(message == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));

    }


    @PutMapping("/updatetaskstage")
    public ResponseEntity<?> handleUpdateTaskStage(@RequestBody UpdateTaskStageRequest updateTaskStageRequest,@AuthenticationPrincipal DefaultUser currentUser){


        var task = taskAndStageService.updateTaskStage(updateTaskStageRequest);

        if(task == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400));

        TaskResponse taskResponse = task.toTaskResponse();
        

        return new ResponseEntity<TaskResponse>(taskResponse,HttpStatusCode.valueOf(200));
    }



    

    



}

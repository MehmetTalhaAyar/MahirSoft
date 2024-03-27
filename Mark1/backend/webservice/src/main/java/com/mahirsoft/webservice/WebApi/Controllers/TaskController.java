package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.TaskService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;
import com.mahirsoft.webservice.Entities.Response.GetAllTaskResponse;
import com.mahirsoft.webservice.Entities.Response.GetTaskResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    TaskService taskService;

    PermissionService permissionService;

    
    
    public TaskController(TaskService taskService, PermissionService permissionService) {
        this.taskService = taskService;
        this.permissionService = permissionService;
    }

    @PostMapping("/addtask") // test için olan bir endpoint
    public ResponseEntity<?> createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest,@AuthenticationPrincipal DefaultUser currentUser) {
        
        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN);

        String body = "Task Created";

        taskService.save(createTaskRequest);
        return new ResponseEntity<String>(body, HttpStatusCode.valueOf(201));   
    }

    @GetMapping("/getalltasks") // test için endpoint
    public List<GetAllTaskResponse> getAllTasks(@AuthenticationPrincipal DefaultUser currentUser){


        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN);

        var items = taskService.getallTask();

        


        // response nesnesine mapping işlemi
        List<GetAllTaskResponse> allTasks = new ArrayList<>();

        for(var task : items){
            GetAllTaskResponse newTask = new GetAllTaskResponse();
            newTask.setTaskId(task.getTaskId());
            newTask.setTaskName(task.getTaskName());
            newTask.setTaskDescription(task.getTaskDescription());
            newTask.setCreatedOn(task.getCreatedOn());
            newTask.setTaskDeadlineDate(task.getTaskDeadlineDate());

            allTasks.add(newTask);
        }

        return allTasks;
    }

    @GetMapping("/{id}") // burada bu projede var mı diye bakılacak
    public ResponseEntity<?> getTaskById(@PathVariable long id,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isTherePermission(currentUser, -1);// şuan yetki bakılmıyor

        String body = "Task not found";
        var task = taskService.getTaskById(id);

        if(task == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(400));
        }


        //response nesnesine map işlemi
        GetTaskResponse getTaskResponse = new GetTaskResponse();

        getTaskResponse.setTaskId(task.getTaskId());
        getTaskResponse.setTaskName(task.getTaskName());
        getTaskResponse.setTaskDescripton(task.getTaskDescription());
        getTaskResponse.setCreatedOn(task.getCreatedOn());
        getTaskResponse.setTaskDeadlineDate(task.getTaskDeadlineDate());
        getTaskResponse.setResponsibleId(task.getResposibleId().toGeneralUserAuthenticationResponse());
        getTaskResponse.setReportsTo(task.getReportsToId().toGeneralUserAuthenticationResponse());
        getTaskResponse.setComments(task.toGeneralCommentResponses());
        getTaskResponse.setStage(task.getStageId().toStageResponse());

        return new ResponseEntity<GetTaskResponse>(getTaskResponse,HttpStatusCode.valueOf(200));
    }

    

    

    






















}

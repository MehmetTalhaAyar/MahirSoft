package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.StageService;
import com.mahirsoft.webservice.Business.concretes.TaskService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUpdateTaskDescriptionRequest;
import com.mahirsoft.webservice.Entities.Requests.PutUpdateTaskNameRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskStageRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralStageResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralTaskResponse;
import com.mahirsoft.webservice.Entities.Response.GetAllTaskResponse;
import com.mahirsoft.webservice.Entities.Response.GetTaskResponse;
import com.mahirsoft.webservice.Entities.Response.TaskResponse;
import com.mahirsoft.webservice.Entities.Response.UpdateTaskResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

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


@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private TaskService taskService;

    private PermissionService permissionService;

    private StageService stageService;

    
    
    public TaskController(TaskService taskService, PermissionService permissionService, StageService stageService) {
        this.taskService = taskService;
        this.permissionService = permissionService;
        this.stageService = stageService;
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


        permissionService.isInThisProjectFindByTaskId(currentUser, id ,AuthorizationCodes.ANY_AUTHORIZATION ); // taskın sahip olduğu projenin içinde mi diye kontrol ediliyor.

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


    @PutMapping("/changedescription")
    public ResponseEntity<?> handleChangeTaskDescription(@Valid @RequestBody PostUpdateTaskDescriptionRequest postUpdateTaskDescriptionRequest,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isInThisProjectFindByTaskId(currentUser, postUpdateTaskDescriptionRequest.getTaskId(),AuthorizationCodes.TASK_CREATE);
        // ilerde burada history oluşturulması sağlanacak
        var task = taskService.ChangeTaskDescription(postUpdateTaskDescriptionRequest);


        return new ResponseEntity<TaskResponse>(task.toTaskResponse(), HttpStatusCode.valueOf(200));
    }   

    @PutMapping("/update/name")
    public ResponseEntity<?> handleUpdateTaskName(@Valid @RequestBody PutUpdateTaskNameRequest putUpdateTaskNameRequest, @AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isInThisProjectFindByTaskId(currentUser, putUpdateTaskNameRequest.getTaskId(), AuthorizationCodes.TASK_CREATE);

        var task = taskService.updateTaskName(putUpdateTaskNameRequest);

        if(task == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var generalTask = task.toTaskResponse();


        return new ResponseEntity<TaskResponse>(generalTask,HttpStatus.OK);
    }

    @PutMapping("/updatetaskstage")
    public ResponseEntity<?> handleUpdateTaskStage(@RequestBody UpdateTaskStageRequest updateTaskStageRequest,@AuthenticationPrincipal DefaultUser currentUser){


        var task = taskService.updateTaskStage(updateTaskStageRequest);

        if(task == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400));

        TaskResponse taskResponse = task.toTaskResponse();
        

        return new ResponseEntity<TaskResponse>(taskResponse,HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id,@AuthenticationPrincipal DefaultUser currentUser){
        String body = "Task Not Found";

        

        permissionService.isInThisProjectFindByTaskId(currentUser, id,AuthorizationCodes.TASK_DELETE);

    
        var message = taskService.softDeleteTask(id);

        if(message == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));

    }

    @GetMapping("/alltasks/{stageId}") // hiç kullanılmıyor
    public GeneralStageResponse getAllTaskByStage(@PathVariable long stageId,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isInThisProjectFindByStageId(currentUser, stageId,AuthorizationCodes.ANY_AUTHORIZATION);

        var stage = stageService.getStage(stageId);

        if(stage == null ) return null;
        
        return stage.toGeneralStageResponse();
    }

    @PutMapping("/updatetask/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable long taskId,@RequestBody UpdateTaskRequest updateTaskRequest,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isTherePermission(currentUser, AuthorizationCodes.TASK_ASSIGNMENT); // burada task atama işlemi yapılıyor

        String body = "Task not Found";
        var updatedTask = taskService.updateTask(taskId, updateTaskRequest);
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

    
}

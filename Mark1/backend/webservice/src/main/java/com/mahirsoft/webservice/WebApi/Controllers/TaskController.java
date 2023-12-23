package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.TaskService;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskRequest;
import com.mahirsoft.webservice.Entities.Response.GetAllTaskResponse;
import com.mahirsoft.webservice.Entities.Response.GetTaskResponse;
import com.mahirsoft.webservice.Entities.Response.UpdateTaskResponse;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    
    @PostMapping("/addtask")
    public ResponseEntity<?> createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        String body = "Task Created";

        taskService.save(createTaskRequest);
        return new ResponseEntity<String>(body, HttpStatusCode.valueOf(201));   
    }

    @GetMapping("/getalltasks")
    public List<GetAllTaskResponse> getAllTasks(){

        return taskService.getallTask();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable long id){

        String body = "Task not found";
        var eleman = taskService.getTaskById(id);

        if(eleman == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(400));
        }

        return new ResponseEntity<GetTaskResponse>(eleman,HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable long id,@RequestBody UpdateTaskRequest updateTaskRequest){

        String body = "Task not Found";
        var updatedTask = taskService.updateTask(id, updateTaskRequest);
        if(updatedTask == null){
            return new ResponseEntity<String>(body , HttpStatusCode.valueOf(400));
        }

        return new ResponseEntity<UpdateTaskResponse>(updatedTask, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id){
        String body = "Task Not Found";
        var message = taskService.softDeleteTask(id);

        if(message == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));

    }

    






















}

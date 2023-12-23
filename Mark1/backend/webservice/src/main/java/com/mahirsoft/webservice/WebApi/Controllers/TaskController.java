package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.TaskService;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    






















}

package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUpdateTaskDescriptionRequest;

import jakarta.validation.Valid;

@Service
public class TaskService {
    TaskRepository taskRepository;

    UserAuthenticationService userAuthenticationService;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    

    public void save(CreateTaskRequest createTaskRequest){
        var task = new Task();
        task.setTaskName(createTaskRequest.getTaskName());
        task.setTaskDescription(createTaskRequest.getTaskDescription());
        task.setCreatedById(null);
        
        taskRepository.save(task);
    }

    public Task createTask(Task task){
        return taskRepository.save(task);

    }


    public List<Task> getallTask(){
        
        return taskRepository.findByDeletionStateCodeNot(1);
    }

    public Task getTaskById(long id) {

        Task task = taskRepository.findById(id);
        if(task == null)
        {

            return null;
        }
        else {  
            return task;
        }
        
    
    }


    public Task findById(long id){
        return taskRepository.findById(id);
    }

    public Task save(Task task){
        return taskRepository.save(task);
    }
    

    
    public Task softDeleteTask(Task task) {
    
        task.setDeletionStateCode(1);            
        return taskRepository.save(task);
        
        
    }


    public Task ChangeTaskDescription(@Valid PostUpdateTaskDescriptionRequest postUpdateTaskDescriptionRequest) {

        var task = taskRepository.findById(postUpdateTaskDescriptionRequest.getTaskId());
        
        if(task == null) return null;
        task.setTaskDescription(postUpdateTaskDescriptionRequest.getDescription());
        
        return taskRepository.save(task);
    }



}

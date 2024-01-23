package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskRequest;

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

    public Task updateTask(long id,UpdateTaskRequest updateTaskRequest){

        Task task = taskRepository.findById(id);

        if(task == null){
            return null;
        }
        

            
        if(updateTaskRequest.getResponsibleId() != null){
            var user = userAuthenticationService.findById(updateTaskRequest.getResponsibleId());
            if(user == null) return null;

            task.setResposibleId(user);
        }

        if(updateTaskRequest.getReportsToId() != null){
        
            var userReporter = userAuthenticationService.findById(updateTaskRequest.getReportsToId());

            if(userReporter == null) return null;

            task.setReportsToId(userReporter);

        }
        
        if(updateTaskRequest.getEndDate() != null)
            task.setTaskDeadlineDate(updateTaskRequest.getEndDate());

        taskRepository.save(task);
        
        return task;
        




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



}

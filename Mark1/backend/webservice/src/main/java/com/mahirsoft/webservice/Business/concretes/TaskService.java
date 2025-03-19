package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUpdateTaskDescriptionRequest;
import com.mahirsoft.webservice.Entities.Requests.PutUpdateTaskNameRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskStageRequest;

import jakarta.validation.Valid;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    private StageRepository stageRepository;
     
    private UserAuthenticationRepository userAuthenticationRepository;

  

    public TaskService(TaskRepository taskRepository, StageRepository stageRepository,
            UserAuthenticationRepository userAuthenticationRepository) {
        this.taskRepository = taskRepository;
        this.stageRepository = stageRepository;
        this.userAuthenticationRepository = userAuthenticationRepository;
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

        Task task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException());
        if(task == null)
        {

            return null;
        }
        else {  
            return task;
        }
        
    
    }

    
    public Task softDeleteTask(long id) {

        Task task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException());
    
        task.setDeletionStateCode(1);            
        return taskRepository.save(task);
        
        
    }

    
    public Task updateTask(long id,UpdateTaskRequest updateTaskRequest){

        Task task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException());
        

        if(updateTaskRequest.getStageId() != null){
            var stage = stageRepository.findById(updateTaskRequest.getStageId()).orElseThrow(()-> new ResourceNotFoundException());

            task.setStageId(stage);
        }
            
        if(updateTaskRequest.getResponsibleId() != null){
            var user = userAuthenticationRepository.findById(updateTaskRequest.getResponsibleId()).orElseThrow(()-> new UserNotFoundException());
            if(user == null) return null;

            task.setResposibleId(user);
        }

        if(updateTaskRequest.getReportsToId() != null){
        
            var userReporter = userAuthenticationRepository.findById(updateTaskRequest.getReportsToId()).orElseThrow(()-> new UserNotFoundException());

            if(userReporter == null) return null;

            task.setReportsToId(userReporter);

        }
        
        if(updateTaskRequest.getEndDate() != null)
            task.setTaskDeadlineDate(updateTaskRequest.getEndDate());

        
        return taskRepository.save(task);

    }


    public Task ChangeTaskDescription(@Valid PostUpdateTaskDescriptionRequest postUpdateTaskDescriptionRequest) {

        var task = taskRepository.findById(postUpdateTaskDescriptionRequest.getTaskId()).orElseThrow(()-> new ResourceNotFoundException());
        
       
        task.setTaskDescription(postUpdateTaskDescriptionRequest.getDescription());
        
        return taskRepository.save(task);
    }


    public Task updateTaskName(@Valid PutUpdateTaskNameRequest putUpdateTaskNameRequest) {
        
        var task = taskRepository.findById(putUpdateTaskNameRequest.getTaskId()).orElseThrow(()-> new ResourceNotFoundException());

        task.setTaskName(putUpdateTaskNameRequest.getName());

        return taskRepository.save(task);
    }


    
    public Task updateTaskStage(UpdateTaskStageRequest updateTaskStageRequest) {
        
        var stage = stageRepository.findByStageIdAndDeletionStateCodeNot(updateTaskStageRequest.getStageId(),1);

        if(stage == null) return null;

        var task = taskRepository.findById(updateTaskStageRequest.getTaskId()).orElseThrow(()-> new ResourceNotFoundException());

        if(task.getStageId().getStageId() == stage.getStageId()){
            // task zaten kendi stageınde bir şey yapılmayacak
            return task;
        }
        else{
            task.setStageId(stage);
            

            return taskRepository.save(task);
        }

    }



}

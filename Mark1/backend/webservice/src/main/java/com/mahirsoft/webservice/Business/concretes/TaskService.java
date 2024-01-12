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

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    

    public void save(CreateTaskRequest createTaskRequest){
        var task = new Task();
        task.setTaskName(createTaskRequest.getTaskName());
        task.setTaskDescription(createTaskRequest.getTaskDescription());
        
        taskRepository.save(task);
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
        else {
            task.setTaskName(updateTaskRequest.getTaskName());
            task.setTaskDescription(updateTaskRequest.getTaskDescription());

            taskRepository.save(task);
            
            return task;
        }




    }
    

    public String softDeleteTask(long id) {
        Task task = taskRepository.findById(id);

        if(task == null){
            return null;
        }
        else{
            task.setDeletionStateCode(1);
            taskRepository.save(task);
            
            return "Delete success";
        }
        
    }

    


}

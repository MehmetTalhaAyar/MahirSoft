package com.mahirsoft.webservice.Business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskRequest;
import com.mahirsoft.webservice.Entities.Response.GetAllTaskResponse;
import com.mahirsoft.webservice.Entities.Response.GetTaskResponse;
import com.mahirsoft.webservice.Entities.Response.UpdateTaskResponse;

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


    public List<GetAllTaskResponse> getallTask(){
        List<GetAllTaskResponse> allTasks = new ArrayList<>();

        for(var task : taskRepository.findByDeletionStateCodeNot(1)){
            GetAllTaskResponse newTask = new GetAllTaskResponse();
            newTask.setTaskId(task.getTaskId());
            newTask.setTaskName(task.getTaskName());
            newTask.setTaskDescripton(task.getTaskDescription());
            newTask.setCreatedOn(task.getCreatedOn());
            newTask.setTaskDeadlineDate(task.getTaskDeadlineDate());

            allTasks.add(newTask);
        }
        

        return allTasks;
    }

    public GetTaskResponse getTaskById(long id) {

        Task task = taskRepository.findById(id);
        if(task == null)
        {

            return null;
        }
        else {

            GetTaskResponse getTaskResponse = new GetTaskResponse();

            getTaskResponse.setTaskId(task.getTaskId());
            getTaskResponse.setTaskName(task.getTaskName());
            getTaskResponse.setTaskDescripton(task.getTaskDescription());
            getTaskResponse.setCreatedOn(task.getCreatedOn());
            getTaskResponse.setTaskDeadlineDate(task.getTaskDeadlineDate());
            
            return getTaskResponse;

        }
        
    
    }

    public UpdateTaskResponse updateTask(long id,UpdateTaskRequest updateTaskRequest){

        Task task = taskRepository.findById(id);

        if(task == null){
            return null;
        }
        else {
            task.setTaskName(updateTaskRequest.getTaskName());
            task.setTaskDescription(updateTaskRequest.getTaskDescription());

            taskRepository.save(task);
            UpdateTaskResponse updateTaskResponse = new UpdateTaskResponse();
            updateTaskResponse.setTaskName(task.getTaskName());
            updateTaskResponse.setTaskDescription(task.getTaskDescription());
            return updateTaskResponse;
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

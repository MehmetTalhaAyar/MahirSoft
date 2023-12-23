package com.mahirsoft.webservice.Business;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Requests.CreateTaskRequest;

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
}

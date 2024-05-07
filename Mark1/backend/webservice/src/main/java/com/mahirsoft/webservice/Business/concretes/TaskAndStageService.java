package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskStageRequest;

@Service
public class TaskAndStageService {

    TaskRepository taskRepository;

    StageRepository stageRepository;

    public TaskAndStageService(TaskRepository taskRepository, StageRepository stageRepository) {
        this.taskRepository = taskRepository;
        this.stageRepository = stageRepository;
    }

    public Task updateTaskStage(UpdateTaskStageRequest updateTaskStageRequest) {
        
        var stage = stageRepository.findById(updateTaskStageRequest.getStageId());

        if(stage == null) return null;

        var task = taskRepository.findById(updateTaskStageRequest.getTaskId());

        if(task == null) return null;

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

package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectRepository;
import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Requests.PutUpdateStageNameRequest;


@Service
public class StageService {

    StageRepository stageRepository;

    ProjectRepository projectRepository;

   
    public StageService(StageRepository stageRepository, ProjectRepository projectRepository) {
        this.stageRepository = stageRepository;
        this.projectRepository = projectRepository;
    }

    public Stage getStage(long id){
        var stage = stageRepository.findById(id);
        if(stage == null){
            return null;
        }
        return stage;     
    }

    public Stage createStage(Stage stage){
        return stageRepository.save(stage);
    }

    public void updateStage(Stage stage){
        stageRepository.save(stage);
    }


    public Stage createStage(CreateStageRequest createStageRequest,UserAuthentication user,long projectId){
        
        var project = projectRepository.findById(projectId);

        Stage stage = new Stage();
        stage.setCreatedById(user);
        stage.setName(createStageRequest.getName());
        stage.setProjectId(project);
        stage.setSequence(project.getStages().size() + 1);
    
        
        return stageRepository.save(stage);
    }

    public Stage updateStage(PutUpdateStageNameRequest putUpdateStageNameRequest) {
        
        var stage = stageRepository.findById(putUpdateStageNameRequest.getStageId());

        if(stage == null) return null;

        stage.setName(putUpdateStageNameRequest.getName());

        return stageRepository.save(stage);

    }

    

    
}

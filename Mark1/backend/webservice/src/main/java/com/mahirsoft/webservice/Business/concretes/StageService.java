package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectRepository;
import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Requests.PutUpdateStageNameRequest;
import com.mahirsoft.webservice.Entities.Requests.PutUpdateStageSequenceRequest;
import com.mahirsoft.webservice.Entities.Response.PutUpdateStageSequenceResponse;

import jakarta.validation.Valid;


@Service
public class StageService {

    StageRepository stageRepository;

    ProjectRepository projectRepository;

   
    public StageService(StageRepository stageRepository, ProjectRepository projectRepository) {
        this.stageRepository = stageRepository;
        this.projectRepository = projectRepository;
    }

    public Stage getStage(long id){
        var stage = stageRepository.findByStageIdAndDeletionStateCodeNot(id,1);
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
        stage.setSequence(stageRepository.findFirstByProjectIdAndDeletionStateCodeNotOrderBySequenceDesc(project,1).getSequence() + 1);    
        
        return stageRepository.save(stage);
    }

    public Stage updateStage(PutUpdateStageNameRequest putUpdateStageNameRequest) {
        
        var stage = stageRepository.findByStageIdAndDeletionStateCodeNot(putUpdateStageNameRequest.getStageId(),1);

        if(stage == null) return null;

        stage.setName(putUpdateStageNameRequest.getName());

        return stageRepository.save(stage);

    }

    public PutUpdateStageSequenceResponse updateStageSequence(@Valid PutUpdateStageSequenceRequest putUpdateStageSequenceRequest) {
        
        var stageGoingUp = stageRepository.findByStageIdAndDeletionStateCodeNot(putUpdateStageSequenceRequest.getStageGoingUp(),1);

        if(stageGoingUp == null) return null;

        var stageGoingDown = stageRepository.findByStageIdAndDeletionStateCodeNot(putUpdateStageSequenceRequest.getStageGoingDown(),1);

        if(stageGoingDown == null) return null;

        int downSequence = stageGoingUp.getSequence(); // yukarı çıkacak olan stage in sequence i  diğerinden daha büyük

        stageGoingUp.setSequence(stageGoingDown.getSequence());
        stageGoingDown.setSequence(downSequence); // down 2


        var newDown = stageRepository.save(stageGoingDown);
        var newUp = stageRepository.save(stageGoingUp);

        PutUpdateStageSequenceResponse putUpdateStageSequenceResponse = new PutUpdateStageSequenceResponse();
        putUpdateStageSequenceResponse.setNewDown(newDown.toGeneralStageResponse());
        putUpdateStageSequenceResponse.setNewUp(newUp.toGeneralStageResponse());


        return putUpdateStageSequenceResponse; 
    }

    public Stage softDeleteStage(long stageId) {

        var stage = stageRepository.findByStageIdAndDeletionStateCodeNot(stageId,1);

        if(stage == null) return null;

        stage.setDeletionStateCode(1);

        return stageRepository.save(stage);
    }

    

    
}

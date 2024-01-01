package com.mahirsoft.webservice.Business;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.Entities.Models.Stage;

@Service
public class StageService {

    StageRepository stageRepository;

    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }


    public Stage getStage(long id){
        var stage = stageRepository.findById(id);
        if(stage == null){
            return null;
        }
        return stage;     
    }

    public void createStage(Stage stage){
        stageRepository.save(stage);
    }

    

    
}

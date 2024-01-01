package com.mahirsoft.webservice.Business;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;

@Service
public class ProjectAndStageService {

    StageService stageService;
    ProjectService projectService;
    

    public ProjectAndStageService(StageService stageService, ProjectService projectService) {
        this.stageService = stageService;
        this.projectService = projectService;
    }


    public Stage addStageToProject(long projectId,CreateStageRequest createStageRequest){
        var project = projectService.getProject(projectId);

        if(project == null){
            return null;
        }
        Stage stage = new Stage();
        stage.setName(createStageRequest.getName());
        stage.setProjectId(project);
        
        stageService.createStage(stage);

        return stage;




    }
    
}

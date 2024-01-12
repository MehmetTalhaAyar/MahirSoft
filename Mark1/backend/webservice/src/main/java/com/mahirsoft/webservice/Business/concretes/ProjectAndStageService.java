package com.mahirsoft.webservice.Business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;

@Service
public class ProjectAndStageService {

    StageService stageService;
    ProjectService projectService;
    UserAuthenticationService userAuthenticationService;

    

    public ProjectAndStageService(StageService stageService, ProjectService projectService,UserAuthenticationService userAuthenticationService) {
        this.stageService = stageService;
        this.projectService = projectService;
        this.userAuthenticationService = userAuthenticationService;
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

    public Project createDefaultProject(CreateProjectRequest createProjectRequest,long userId){

        List<Stage> stages = new ArrayList<Stage>();

        var user = userAuthenticationService.findById(userId);
        if(user == null) return null;
        
        Stage newStage = new Stage();
        newStage.setCreatedById(user);
        newStage.setName("New");

        Stage pendingStage = new Stage();
        pendingStage.setCreatedById(user);
        pendingStage.setName("Pending");

        Stage inProgressStage = new Stage();
        inProgressStage.setCreatedById(user);
        inProgressStage.setName("In Proggress");

        Stage finishedStage = new Stage();
        finishedStage.setCreatedById(user);
        finishedStage.setName("Finished");

        Stage failedStage = new Stage();
        failedStage.setCreatedById(user);
        failedStage.setName("Failed");

        stages.add(newStage);
        stages.add(pendingStage);
        stages.add(inProgressStage);
        stages.add(finishedStage);
        stages.add(failedStage);
        

        Project project = new Project();

        project.setName(createProjectRequest.getName());
        project.setCompanyId(user.getCompanyId());
        project.setLeadingPersonId(user);
        project.setStages(stages);
        
        // userın companysine göre projectin alanı doldurulacak
        // project.setCompanyId(user.getCompanyId());
        
        projectService.createProject(project);

        newStage.setProjectId(project);
        pendingStage.setProjectId(project);
        inProgressStage.setProjectId(project);
        finishedStage.setProjectId(project);
        failedStage.setProjectId(project);

        stageService.createStage(newStage);
        stageService.createStage(pendingStage);
        stageService.createStage(inProgressStage);
        stageService.createStage(finishedStage);
        stageService.createStage(failedStage);

        return project;
    }
    
}

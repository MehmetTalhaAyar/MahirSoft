package com.mahirsoft.webservice.Business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Models.ProjectUser;
import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Requests.PostCreateProjectRequest;

@Service
public class ProjectAndStageService {

    StageService stageService;
    ProjectService projectService;
    UserAuthenticationService userAuthenticationService;
    ProjectUserService projectUserService;

    

    public ProjectAndStageService(StageService stageService, ProjectService projectService,
            UserAuthenticationService userAuthenticationService, ProjectUserService projectUserService) {
        this.stageService = stageService;
        this.projectService = projectService;
        this.userAuthenticationService = userAuthenticationService;
        this.projectUserService = projectUserService;
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

        
        var user = userAuthenticationService.findById(userId);
        if(user == null) return null;

        var project = defaultProject(createProjectRequest,user,user);

        return project;
    }


    public Project createProject(PostCreateProjectRequest postCreateProjectRequest,long createdUserId){

        var createdUser = userAuthenticationService.findById(createdUserId);
        if(createdUser == null ) return null;

        var leadPerson = userAuthenticationService.findById(postCreateProjectRequest.getAdminId());
        if(leadPerson == null) return null;


        var project = defaultProject(postCreateProjectRequest.getProject(),createdUser,leadPerson);
        if(project == null) return null;

        if(!postCreateProjectRequest.getProjectUserIds().contains(postCreateProjectRequest.getAdminId())){
            postCreateProjectRequest.getProjectUserIds().add(postCreateProjectRequest.getAdminId());
        }

        for(var currentId : postCreateProjectRequest.getProjectUserIds()){
            var projectMember = userAuthenticationService.findById(currentId);
            ProjectUser projectUser = new ProjectUser();
            projectUser.setProjectId(project);
            projectUser.setUserId(projectMember);

            projectUserService.addUserToProject(projectUser);
        }

        return project;
    }


    private Project defaultProject(CreateProjectRequest createProjectRequest,UserAuthentication createdUser,UserAuthentication leadPerson){

        List<Stage> stages = new ArrayList<Stage>();

        Stage newStage = new Stage();
        newStage.setCreatedById(createdUser);
        newStage.setName("New");

        Stage pendingStage = new Stage();
        pendingStage.setCreatedById(createdUser);
        pendingStage.setName("Pending");

        Stage inProgressStage = new Stage();
        inProgressStage.setCreatedById(createdUser);
        inProgressStage.setName("In Proggress");

        Stage finishedStage = new Stage();
        finishedStage.setCreatedById(createdUser);
        finishedStage.setName("Finished");

        Stage failedStage = new Stage();
        failedStage.setCreatedById(createdUser);
        failedStage.setName("Failed");

        stages.add(newStage);
        stages.add(pendingStage);
        stages.add(inProgressStage);
        stages.add(finishedStage);
        stages.add(failedStage);
        

        Project project = new Project();

        project.setName(createProjectRequest.getName());
        project.setCompanyId(leadPerson.getCompanyId());
        project.setLeadingPersonId(leadPerson);
        project.setStages(stages);        
        
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

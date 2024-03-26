package com.mahirsoft.webservice.Business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectUserRepository;
import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Models.ProjectUser;
import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Models.Task;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Requests.PostCreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PostGetStageAndProjectMembersRequest;
import com.mahirsoft.webservice.Entities.Requests.UpdateTaskRequest;
import com.mahirsoft.webservice.Entities.Response.ProjectMembersAndStageResponse;
import com.mahirsoft.webservice.Entities.Response.UserAuthenticationResponse;

@Service
public class ProjectAndStageService {

    StageService stageService;
    ProjectService projectService;
    UserAuthenticationService userAuthenticationService;
    ProjectUserRepository projectUserRepository;
    TaskService taskService;

    public ProjectAndStageService(StageService stageService, ProjectService projectService,
            UserAuthenticationService userAuthenticationService, ProjectUserRepository projectUserRepository,
            TaskService taskService) {
        this.stageService = stageService;
        this.projectService = projectService;
        this.userAuthenticationService = userAuthenticationService;
        this.projectUserRepository = projectUserRepository;
        this.taskService = taskService;
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

            projectUserRepository.save(projectUser);
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


    public ProjectMembersAndStageResponse getProjectMembersAndStageByStageId(PostGetStageAndProjectMembersRequest postGetStageAndProjectMembersRequest) {
       
        List<UserAuthenticationResponse> users = new ArrayList<>();
        ProjectMembersAndStageResponse projectMembersAndStageResponse = new ProjectMembersAndStageResponse();

        var stage = stageService.getStage(postGetStageAndProjectMembersRequest.getStageId());

        if(stage == null) return null;

        if(postGetStageAndProjectMembersRequest.getSearchKey().strip().isBlank()){
            var projectUsers =  projectUserRepository.findFirst5ByProjectId(stage.getProjectId());

            for(var eleman : projectUsers){
                users.add(eleman.getUserId().toUserAuthenticationResponse());
            }

            projectMembersAndStageResponse.setStages(stage.getProjectId().toStageResponses());
            projectMembersAndStageResponse.setUsers(users);

            return projectMembersAndStageResponse;
        }

        
        var projectMembers = projectUserRepository.findFirst5ByProjectIdAndNameContaining(stage.getProjectId(), postGetStageAndProjectMembersRequest.getSearchKey());
        

        for(var projectUser : projectMembers){
            users.add(projectUser.getUserId().toUserAuthenticationResponse());
        }


        
        projectMembersAndStageResponse.setStages(stage.getProjectId().toStageResponses());
        projectMembersAndStageResponse.setUsers(users);

        return projectMembersAndStageResponse;


    }

    public Task updateTask(long id,UpdateTaskRequest updateTaskRequest){

        Task task = taskService.findById(id);

        if(task == null){
            return null;
        }
        

        if(updateTaskRequest.getStageId() != null){
            var stage = stageService.getStage(updateTaskRequest.getStageId());

            if(stage == null) return null;

            task.setStageId(stage);
        }
            
        if(updateTaskRequest.getResponsibleId() != null){
            var user = userAuthenticationService.findById(updateTaskRequest.getResponsibleId());
            if(user == null) return null;

            task.setResposibleId(user);
        }

        if(updateTaskRequest.getReportsToId() != null){
        
            var userReporter = userAuthenticationService.findById(updateTaskRequest.getReportsToId());

            if(userReporter == null) return null;

            task.setReportsToId(userReporter);

        }
        
        if(updateTaskRequest.getEndDate() != null)
            task.setTaskDeadlineDate(updateTaskRequest.getEndDate());

        taskService.save(task);
        
        return task;
        




    }
    
}

package com.mahirsoft.webservice.Business.concretes;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectRepository;
import com.mahirsoft.webservice.DataAccess.ProjectUserRepository;
import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.DataAccess.UserRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Models.ProjectUser;
import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Requests.DeleteAMemberFromTheProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PostAddMemberToProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PostCreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PostSearchProjectMembersRequest;
import com.mahirsoft.webservice.Entities.Requests.PutProjectDescriptionRequest;
import com.mahirsoft.webservice.Entities.Requests.PutProjectNameRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.GetProjectTaskCount;

import jakarta.validation.Valid;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    private StageRepository stageRepository;

    private UserRepository userAuthenticationRepository; 

    private ProjectUserRepository projectUserRepository;

    private TaskRepository taskRepository;

  


    

    public ProjectService(ProjectRepository projectRepository, StageRepository stageRepository,
            UserRepository userAuthenticationRepository, ProjectUserRepository projectUserRepository,
            TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.projectUserRepository = projectUserRepository;
        this.taskRepository = taskRepository;
    }

    //büyük ihtimalle kaldırılması gerekiyor
    public Project createProject(CreateProjectRequest createProjectRequest){
        var project = new Project();

        project.setName(createProjectRequest.getName());
        
        return projectRepository.save(project);
    }

    public Project createDefaultProject(CreateProjectRequest createProjectRequest,long userId,User createdBy){

        
        var leadPerson = userAuthenticationRepository.findById(userId).orElseThrow(()-> new UserNotFoundException());

        var project = defaultProject(createProjectRequest,createdBy,leadPerson);

        return project;
    }

     public Project createProject(PostCreateProjectRequest postCreateProjectRequest,User createdBy){


        var leadPerson = userAuthenticationRepository.findById(postCreateProjectRequest.getAdminId()).orElseThrow(()-> new UserNotFoundException());

        var project = defaultProject(postCreateProjectRequest.getProject(),createdBy,leadPerson);
        if(project == null) return null;

        List<ProjectUser> projectUsers = new ArrayList<>();

        if(!postCreateProjectRequest.getProjectUserIds().contains(postCreateProjectRequest.getAdminId())){
            postCreateProjectRequest.getProjectUserIds().add(postCreateProjectRequest.getAdminId());
        }

        for(var currentId : postCreateProjectRequest.getProjectUserIds()){
            var projectMember = userAuthenticationRepository.findById(currentId).orElseThrow(()-> new UserNotFoundException());
            ProjectUser projectUser = new ProjectUser();
            projectUser.setProjectId(project);
            projectUser.setUserId(projectMember);

            projectUsers.add(projectUserRepository.save(projectUser));

        }

        project.setProjectMembers(projectUsers);

        return project;
    }



    public Stage addStageToProject(long projectId,CreateStageRequest createStageRequest,User createdBy){
        var project = projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException()); // burayı değiştirdim silinen stageler gelirse ona göre değiştirilmesi lazım

        Stage stage = new Stage();
        stage.setName(createStageRequest.getName());
        stage.setProjectId(project);
        stage.setCreatedById(createdBy);
        
        return stageRepository.save(stage);

    }

    // burası silinecek
    public Project getProject(long id){

        var project = projectRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException());
        
        var stages = stageRepository.findByProjectIdAndDeletionStateCodeNotOrderBySequenceAsc(project,1);

        project.setStages(stages);
        
        return project;
    }

    public Project softDeleteProject(long id,User user) {

        
        var project = projectRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException());

        if(project.getLeadingPersonId().getUserId() == user.getUserId()){


            project.setDeletionStateCode(1);
            return projectRepository.save(project);
        }
        else {
            return null;
        }
}

    public Project createProjectWithLeading(long id,CreateProjectRequest createProjectRequest){

        var user = userAuthenticationRepository.findById(id).orElseThrow(()-> new UserNotFoundException());

        Project project = new Project();
        project.setLeadingPersonId(user);
        project.setName(createProjectRequest.getName());

        projectRepository.save(project);
        
        return project;

    }

    public GetProjectTaskCount getTaskCounts(Project project) {

        GetProjectTaskCount taskCount = new GetProjectTaskCount();

        var failedStage = stageRepository.findByProjectIdAndName(project, "Failed");

        var finishedStage = stageRepository.findByProjectIdAndName(project, "Finished");
        
        if(failedStage != null)
            taskCount.setFailedTask(taskRepository.countByStageId(failedStage));

        if(finishedStage != null) 
            taskCount.setFinishedTask(taskRepository.countByStageId(finishedStage));

        int stagesTaskCount = 0;
        for(var eleman :project.getStages()){
            stagesTaskCount += taskRepository.countByStageId(eleman);
        }

        taskCount.setTotalTaskCount(stagesTaskCount);


        return taskCount;
    }

    public Project updateDescription(long projectId, PutProjectDescriptionRequest projectDescriptionRequest) {
        
        var project = projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException());

        project.setDescription(projectDescriptionRequest.getDescription());

        return projectRepository.save(project);

    }

    public Project updateName(long projectId, PutProjectNameRequest projectNameRequest) {

        var project = projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException());

        project.setName(projectNameRequest.getName());

        return projectRepository.save(project);
    }

    public ProjectUser addANewMember(PostAddMemberToProjectRequest postAddMemberToProjectRequest,User user) {

        var member = userAuthenticationRepository.findByEmail(postAddMemberToProjectRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException());

        if(user.getCompanyId().getCompanyId() == member.getCompanyId().getCompanyId()){ // aynı şirketin içerisinde olup olmadıkları kontrol ediliyor.

            var project = projectRepository.findById(postAddMemberToProjectRequest.getProjectId()).orElseThrow(()-> new ResourceNotFoundException());

            if(project == null) return null;

            var userWhoAdd = projectUserRepository.findByProjectIdAndUserId(project, user);

            if(userWhoAdd == null) return null;

            var userWhoAdded = projectUserRepository.findByProjectIdAndUserId(project, member); // projenin içinde mi diye bakılıyor

            if( userWhoAdded != null ) return null;

            ProjectUser projectUser = new ProjectUser();
            projectUser.setProjectId(project);
            projectUser.setUserId(member);

            List<ProjectUser> projectUsers = project.getProjectMembers();
            projectUsers.add(projectUser);
            
            project.setProjectMembers(projectUsers);
            projectRepository.save(project);

            return projectUserRepository.save(projectUser);

        }

        return null;
    }

    public List<User> getMembersWhoNotInThisProject(PostSearchProjectMembersRequest postSearchProjectMembersRequest,User user) {


        var project = projectRepository.findById(postSearchProjectMembersRequest.getProjectId()).orElseThrow(()-> new ResourceNotFoundException());

        if(project == null) throw new ResourceNotFoundException();

        List<User> members = new ArrayList<>();

        for(var eleman : project.getProjectMembers()){
            members.add(eleman.getUserId());
        }


        List<User> companyUsers = new ArrayList<>();
        
        if(postSearchProjectMembersRequest.getSearchKey().strip().isBlank()){
            companyUsers.addAll(userAuthenticationRepository.findFirst3ByCompanyId(user.getCompanyId(), members));
        }
        else {
            companyUsers.addAll(userAuthenticationRepository.findFirst3ByCompanyIdAndNameContaining(user.getCompanyId(), postSearchProjectMembersRequest.getSearchKey(),members));
        }


        return companyUsers;
    }

    public GeneralProjectResponse deleteMemberFromProject(@Valid DeleteAMemberFromTheProjectRequest deleteAMemberFromTheProjectRequest,User user) {
        

        var deletedUser = userAuthenticationRepository.findByEmail(deleteAMemberFromTheProjectRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException());

        var project = projectRepository.findById(deleteAMemberFromTheProjectRequest.getProjectId()).orElseThrow(()-> new ResourceNotFoundException());

        var projectUser = projectUserRepository.findByProjectIdAndUserId(project, deletedUser);
    
        if(projectUser == null) return null;

        if(deletedUser.getUserId() == project.getLeadingPersonId().getUserId()){
            return null;
        }
        
        projectUserRepository.delete(projectUser);

        return projectUser.getProjectId().toGeneralProjectResponse();
    }


    // Private methods
    
    private Project defaultProject(CreateProjectRequest createProjectRequest,User createdUser,User leadPerson){

        List<Stage> stages = new ArrayList<Stage>();

        Stage newStage = new Stage();
        newStage.setCreatedById(createdUser);
        newStage.setName("New");
        newStage.setSequence(1);

        Stage pendingStage = new Stage();
        pendingStage.setCreatedById(createdUser);
        pendingStage.setName("Pending");
        pendingStage.setSequence(2);

        Stage inProgressStage = new Stage();
        inProgressStage.setCreatedById(createdUser);
        inProgressStage.setName("In Proggress");
        inProgressStage.setSequence(3);

        Stage finishedStage = new Stage();
        finishedStage.setCreatedById(createdUser);
        finishedStage.setName("Finished");
        finishedStage.setSequence(4);

        Stage failedStage = new Stage();
        failedStage.setCreatedById(createdUser);
        failedStage.setName("Failed");
        failedStage.setSequence(5);

        stages.add(newStage);
        stages.add(pendingStage);
        stages.add(inProgressStage);
        stages.add(finishedStage);
        stages.add(failedStage);
        

        Project project = new Project();

        project.setName(createProjectRequest.getName());
        if(leadPerson.getCompanyId() != null)
            project.setCompanyId(leadPerson.getCompanyId());
        project.setLeadingPersonId(leadPerson);
        project.setDescription("New Project");
        project.setStages(stages);        
        
        projectRepository.save(project);

        newStage.setProjectId(project);
        pendingStage.setProjectId(project);
        inProgressStage.setProjectId(project);
        finishedStage.setProjectId(project);
        failedStage.setProjectId(project);

        stageRepository.save(newStage);
        stageRepository.save(pendingStage);
        stageRepository.save(inProgressStage);
        stageRepository.save(finishedStage);
        stageRepository.save(failedStage);

        return project;
    }


 
}

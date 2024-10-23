package com.mahirsoft.webservice.Business.concretes;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectRepository;
import com.mahirsoft.webservice.DataAccess.ProjectUserRepository;
import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.DataAccess.TaskRepository;
import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Models.ProjectUser;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.DeleteAMemberFromTheProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PostAddMemberToProjectRequest;
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

    private UserAuthenticationRepository userAuthenticationRepository; 

    private ProjectUserRepository projectUserRepository;

    private TaskRepository taskRepository;

  


    

    public ProjectService(ProjectRepository projectRepository, StageRepository stageRepository,
            UserAuthenticationRepository userAuthenticationRepository, ProjectUserRepository projectUserRepository,
            TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.projectUserRepository = projectUserRepository;
        this.taskRepository = taskRepository;
    }

    public void createProject(CreateProjectRequest createProjectRequest){
        var project = new Project();

        project.setName(createProjectRequest.getName());

    
        projectRepository.save(project);

    }

    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    public Project getProject(long id){

        var project = projectRepository.findById(id);
        if (project == null){
            return null;
        }
        var stages = stageRepository.findByProjectIdAndDeletionStateCodeNotOrderBySequenceAsc(project,1);
        project.setStages(stages);
        return project;
    }

    public Project findById(long id){
        return projectRepository.findById(id);
    }

    public Project save(Project project){
        return projectRepository.save(project);
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
        
        var project = projectRepository.findById(projectId);

        if(project == null)  throw new ResourceNotFoundException(); 

        project.setDescription(projectDescriptionRequest.getDescription());

        return projectRepository.save(project);

    }

    public Project updateName(long projectId, PutProjectNameRequest projectNameRequest) {

        var project = projectRepository.findById(projectId);

        if(project == null)  return null; // burada uygun bir exception fırlat

        project.setName(projectNameRequest.getName());

        return projectRepository.save(project);
    }

    public ProjectUser addANewMember(PostAddMemberToProjectRequest postAddMemberToProjectRequest,UserAuthentication user) {

        var member = userAuthenticationRepository.findByEmail(postAddMemberToProjectRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException());

        if(user.getCompanyId().getCompanyId() == member.getCompanyId().getCompanyId()){ // aynı şirketin içerisinde olup olmadıkları kontrol ediliyor.

            var project = projectRepository.findById(postAddMemberToProjectRequest.getProjectId());

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

    public List<UserAuthentication> getMembersWhoNotInThisProject(PostSearchProjectMembersRequest postSearchProjectMembersRequest,UserAuthentication user) {


        var project = projectRepository.findById(postSearchProjectMembersRequest.getProjectId());

        if(project == null) throw new ResourceNotFoundException();

        List<UserAuthentication> members = new ArrayList<>();

        for(var eleman : project.getProjectMembers()){
            members.add(eleman.getUserId());
        }


        List<UserAuthentication> companyUsers = new ArrayList<>();
        
        if(postSearchProjectMembersRequest.getSearchKey().strip().isBlank()){
            companyUsers.addAll(userAuthenticationRepository.findFirst3ByCompanyId(user.getCompanyId(), members));
        }
        else {
            companyUsers.addAll(userAuthenticationRepository.findFirst3ByCompanyIdAndNameContaining(user.getCompanyId(), postSearchProjectMembersRequest.getSearchKey(),members));
        }


        return companyUsers;
    }

    public GeneralProjectResponse deleteMemberFromProject(@Valid DeleteAMemberFromTheProjectRequest deleteAMemberFromTheProjectRequest,UserAuthentication user) {
        

        var deletedUser = userAuthenticationRepository.findByEmail(deleteAMemberFromTheProjectRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException());

        var project = projectRepository.findById(deleteAMemberFromTheProjectRequest.getProjectId());

        if(project == null) return null;

        var projectUser = projectUserRepository.findByProjectIdAndUserId(project, deletedUser);
    
        if(projectUser == null) return null;

        if(deletedUser.getUserId() == project.getLeadingPersonId().getUserId()){
            return null;
        }
        
        projectUserRepository.delete(projectUser);

        return projectUser.getProjectId().toGeneralProjectResponse();
    }


 
}

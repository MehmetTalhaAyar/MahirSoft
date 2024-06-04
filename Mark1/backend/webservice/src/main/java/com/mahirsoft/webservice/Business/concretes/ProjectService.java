package com.mahirsoft.webservice.Business.concretes;


import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectRepository;
import com.mahirsoft.webservice.DataAccess.ProjectUserRepository;
import com.mahirsoft.webservice.DataAccess.StageRepository;
import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Models.ProjectUser;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PostAddMemberToProjectRequest;
import com.mahirsoft.webservice.Entities.Requests.PutProjectDescriptionRequest;
import com.mahirsoft.webservice.Entities.Requests.PutProjectNameRequest;
import com.mahirsoft.webservice.Entities.Response.GetProjectTaskCount;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    private StageRepository stageRepository;

    private UserAuthenticationRepository userAuthenticationRepository; 

    private ProjectUserRepository projectUserRepository;

  


    public ProjectService(ProjectRepository projectRepository, StageRepository stageRepository,
            UserAuthenticationRepository userAuthenticationRepository, ProjectUserRepository projectUserRepository) {
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.projectUserRepository = projectUserRepository;
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
        taskCount.setFailedTask(stageRepository.countByProjectIdAndName(project, "Failed"));
        taskCount.setFinishedTask(stageRepository.countByProjectIdAndName(project, "Finished"));
        taskCount.setTotalTaskCount(stageRepository.countByProjectId(project));


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

            var userWhoAdded = projectUserRepository.findByProjectIdAndUserId(project, member);

            if( userWhoAdded == null ) return null;

            ProjectUser projectUser = new ProjectUser();
            projectUser.setProjectId(project);
            projectUser.setUserId(member);

            return projectUserRepository.save(projectUser);

        }

        return null;
    }


 
}

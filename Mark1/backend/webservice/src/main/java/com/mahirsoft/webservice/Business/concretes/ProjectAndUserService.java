package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;

@Service
public class ProjectAndUserService {

    ProjectService projectService;
    
    UserAuthenticationService userAuthenticationService;


   

    public ProjectAndUserService(ProjectService projectService, UserAuthenticationService userAuthenticationService) {
        this.projectService = projectService;
        this.userAuthenticationService = userAuthenticationService;
    }




    public Project createProjectWithLeading(long id,CreateProjectRequest createProjectRequest){

        var user = userAuthenticationService.findById(id);

        if(user == null){
            return null;
        }

        Project project = new Project();
        project.setLeadingPersonId(user);
        project.setName(createProjectRequest.getName());

        projectService.createProject(project);
        
        return project;

    }




    public Project softDeleteProject(long id,long userId) {

        var user = userAuthenticationService.findById(userId);
        if(user == null ) return null;
        
        var project = projectService.findById(id);
        if(project == null) return null;

        if(project.getLeadingPersonId().getUserId() == user.getUserId()){
            project.setDeletionStateCode(1);
            return projectService.save(project);
        }
        else {
            return null;
        }

        

    }



    
    
}

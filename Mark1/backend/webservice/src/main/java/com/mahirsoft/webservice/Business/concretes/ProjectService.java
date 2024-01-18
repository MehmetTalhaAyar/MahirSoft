package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectRepository;
import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void createProject(CreateProjectRequest createProjectRequest){
        var project = new Project();

        project.setName(createProjectRequest.getName());

    
        projectRepository.save(project);

    }

    public void createProject(Project project){
        projectRepository.save(project);
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

 
}

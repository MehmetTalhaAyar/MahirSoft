package com.mahirsoft.webservice.Business.concretes;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.ProjectUserRepository;
import com.mahirsoft.webservice.Entities.Models.ProjectUser;

@Service
public class ProjectUserService {
    
    ProjectUserRepository projectUserRepository;

    public ProjectUserService(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

    public void addUserToProject(ProjectUser user){
        projectUserRepository.save(user);
    }
    


}

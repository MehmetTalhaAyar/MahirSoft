package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.ProjectUser;

public interface ProjectUserRepository extends JpaRepository<ProjectUser,Long> {
    
}

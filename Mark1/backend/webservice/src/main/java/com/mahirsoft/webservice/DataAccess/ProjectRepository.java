package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Project;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findById(long id);
    
}

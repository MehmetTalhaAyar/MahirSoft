package com.mahirsoft.webservice.DataAccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Project;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Optional<Project> findById(long id);
    
}

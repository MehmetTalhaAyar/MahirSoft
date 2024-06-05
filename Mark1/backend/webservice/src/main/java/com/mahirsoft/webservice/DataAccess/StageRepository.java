package com.mahirsoft.webservice.DataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Models.Stage;

public interface StageRepository extends JpaRepository<Stage,Long> {
    
    Stage findById(long id);
    
    int countByProjectIdAndName(Project project,String name);

    Stage findByProjectIdAndName(Project project,String name);

    int countByProjectId(Project project);
    
}

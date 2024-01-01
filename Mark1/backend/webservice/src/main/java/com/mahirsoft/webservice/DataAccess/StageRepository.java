package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Stage;

public interface StageRepository extends JpaRepository<Stage,Long> {
    
    Stage findById(long id);
    
}

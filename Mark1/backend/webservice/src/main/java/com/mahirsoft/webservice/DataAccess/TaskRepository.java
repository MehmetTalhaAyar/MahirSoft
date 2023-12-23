package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Task;

public interface TaskRepository extends JpaRepository<Task,Long>{

    Task findById(long id);
    
}

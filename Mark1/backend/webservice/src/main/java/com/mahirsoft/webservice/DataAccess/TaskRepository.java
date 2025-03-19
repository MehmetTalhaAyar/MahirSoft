package com.mahirsoft.webservice.DataAccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Stage;
import com.mahirsoft.webservice.Entities.Models.Task;

public interface TaskRepository extends JpaRepository<Task,Long>{

    Optional<Task> findById(long id);

    List<Task> findByDeletionStateCodeNot(int deletionStateCode);

    int countByStageId(Stage stage);
    
}

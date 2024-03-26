package com.mahirsoft.webservice.DataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahirsoft.webservice.Entities.Models.Project;
import com.mahirsoft.webservice.Entities.Models.ProjectUser;

public interface ProjectUserRepository extends JpaRepository<ProjectUser,Long> {
    

    @Query(value = "Select p from ProjectUser p join p.userId u WHERE p.projectId = :project and u.name LIKE %:name%")
    List<ProjectUser> findFirst5ByProjectIdAndNameContaining(@Param("project") Project project,@Param("name") String name);


    List<ProjectUser> findFirst5ByProjectId(Project project);


}

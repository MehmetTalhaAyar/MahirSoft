package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    
    UserRole findById(long id);
}

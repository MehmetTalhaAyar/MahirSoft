package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.UserRoleAuthorization;

public interface UserRoleAuthorizationRepository extends JpaRepository<UserRoleAuthorization,Long> {
    
}

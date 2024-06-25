package com.mahirsoft.webservice.DataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Authorization;
import com.mahirsoft.webservice.Entities.Models.UserRole;
import com.mahirsoft.webservice.Entities.Models.UserRoleAuthorization;

public interface UserRoleAuthorizationRepository extends JpaRepository<UserRoleAuthorization,Long> {

    List<UserRoleAuthorization> findByuserRoleId(UserRole userRole);

    boolean existsByAuthorizationIdAndUserRoleId(Authorization authorization, UserRole userRole);
    
}

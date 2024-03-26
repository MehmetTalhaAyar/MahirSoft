package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Authorization;

public interface AuthorizationRepository extends JpaRepository<Authorization,Long> {
    
}

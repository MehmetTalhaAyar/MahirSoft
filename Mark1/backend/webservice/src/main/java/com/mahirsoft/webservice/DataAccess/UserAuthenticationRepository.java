package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public interface UserAuthenticationRepository  extends JpaRepository<UserAuthentication,Long>{

    UserAuthentication findById(long id);

    UserAuthentication findByEmail(String email);
    
    
}

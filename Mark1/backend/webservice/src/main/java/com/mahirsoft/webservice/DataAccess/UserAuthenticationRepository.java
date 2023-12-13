package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public interface UserAuthenticationRepository  extends JpaRepository<UserAuthentication,Long>{

    UserAuthentication findById(long id);

    @Query("select au from UserAuthentication au where au.email = :email and au.password = :password")
    UserAuthentication findByEmailandPassword(String email,String password);


    
    
}

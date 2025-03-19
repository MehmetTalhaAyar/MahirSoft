package com.mahirsoft.webservice.DataAccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Token;

public interface TokenRepository extends JpaRepository<Token,String> {

    Optional<Token> findByToken(String token);
    
}

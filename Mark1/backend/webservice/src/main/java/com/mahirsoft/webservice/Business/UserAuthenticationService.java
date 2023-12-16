package com.mahirsoft.webservice.Business;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Exceptions.NotUniqueEmailException;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

@Service
public class UserAuthenticationService {

    UserAuthenticationRepository userAuthenticationRepository;
    
    UserAuthenticationService (UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository =  userAuthenticationRepository;
    }


    public void save(UserAuthentication userAuthentication){
        try{

            userAuthenticationRepository.save(userAuthentication);
        }
        catch(DataIntegrityViolationException ex){
            throw new NotUniqueEmailException();
        }

    }

    public UserAuthentication getUserInfo(UserAuthentication userAuthentication){
        
        return userAuthenticationRepository.findByEmailandPassword(userAuthentication.getEmail(),userAuthentication.getPassword());
        
        
    }


    public List<UserAuthentication> getAllUsers() {
        return userAuthenticationRepository.findAll();
    }

    
}

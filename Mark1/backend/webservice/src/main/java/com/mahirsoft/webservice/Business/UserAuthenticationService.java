package com.mahirsoft.webservice.Business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.UserAuthentication;

@Service
public class UserAuthenticationService {

    UserAuthenticationRepository userAuthenticationRepository;
    
    UserAuthenticationService (UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository =  userAuthenticationRepository;
    }


    public void save(UserAuthentication userAuthentication){
        userAuthenticationRepository.save(userAuthentication);

    }

    public UserAuthentication getUserInfo(UserAuthentication userAuthentication){
        
        var user = userAuthenticationRepository.findByEmailandPassword(userAuthentication.getEmail(),userAuthentication.getPassword());
        if (user != null)
            return user;
        else
            return null;
        
    }


    public List<UserAuthentication> getAllUsers() {
        return userAuthenticationRepository.findAll();
    }

    
}

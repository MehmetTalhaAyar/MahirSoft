package com.mahirsoft.webservice.Business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;


@Service
public class UserAuthenticationService {

    UserAuthenticationRepository userAuthenticationRepository;
    
    UserAuthenticationService (UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository =  userAuthenticationRepository;
    }


    public void save(UserAuthentication userAuthentication){

        userAuthenticationRepository.save(userAuthentication);
       

    }

    public UserAuthentication getUserInfo(PostUserAuthenticationRequest PostUserAuthenticationRequest){
        
        UserAuthentication userAuthentication = userAuthenticationRepository.findByEmailandPassword(PostUserAuthenticationRequest.getEmail(),PostUserAuthenticationRequest.getPassword());
        if (userAuthentication == null){
            return null;
        }
        else{

        
            return userAuthentication;
        }
                
    }


    public List<UserAuthentication> getAllUsers() {

        
        return userAuthenticationRepository.findAll();
    }

    public UserAuthentication findById(long id){
        return userAuthenticationRepository.findById(id);
    }

    
}

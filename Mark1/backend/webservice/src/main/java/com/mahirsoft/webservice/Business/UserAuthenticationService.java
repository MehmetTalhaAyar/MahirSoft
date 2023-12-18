package com.mahirsoft.webservice.Business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Models.CreateModels.CreateUserAuthtentication;
import com.mahirsoft.webservice.Entities.Models.DatabaseModels.UserAuthentication;
import com.mahirsoft.webservice.Entities.Models.GetModels.GetUserAuthentication;

@Service
public class UserAuthenticationService {

    UserAuthenticationRepository userAuthenticationRepository;
    
    UserAuthenticationService (UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository =  userAuthenticationRepository;
    }


    public void save(CreateUserAuthtentication createUserAuthtentication){

        UserAuthentication user = new UserAuthentication();
        user.setEmail(createUserAuthtentication.getEmail());
        user.setPassword(createUserAuthtentication.getPassword());
        userAuthenticationRepository.save(user);
       

    }

    public UserAuthentication getUserInfo(GetUserAuthentication getUserAuthentication){
        
        return userAuthenticationRepository.findByEmailandPassword(getUserAuthentication.getEmail(),getUserAuthentication.getPassword());
        
        
    }


    public List<UserAuthentication> getAllUsers() {
        return userAuthenticationRepository.findAll();
    }

    
}

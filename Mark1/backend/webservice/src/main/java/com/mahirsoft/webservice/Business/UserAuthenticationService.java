package com.mahirsoft.webservice.Business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;
import com.mahirsoft.webservice.Entities.Response.GetAllUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.PostUserAuthenticationResponse;

@Service
public class UserAuthenticationService {

    UserAuthenticationRepository userAuthenticationRepository;
    
    UserAuthenticationService (UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository =  userAuthenticationRepository;
    }


    public void save(UserAuthentication userAuthentication){

        userAuthenticationRepository.save(userAuthentication);
       

    }

    public PostUserAuthenticationResponse getUserInfo(PostUserAuthenticationRequest PostUserAuthenticationRequest){
        
        UserAuthentication userAuthentication = userAuthenticationRepository.findByEmailandPassword(PostUserAuthenticationRequest.getEmail(),PostUserAuthenticationRequest.getPassword());
        if (userAuthentication == null){
            return null;
        }
        else{

            PostUserAuthenticationResponse postUserAuthenticationResponse = new PostUserAuthenticationResponse();
            postUserAuthenticationResponse.setUserId(userAuthentication.getUserId());
            postUserAuthenticationResponse.setEmail(userAuthentication.getEmail());
            postUserAuthenticationResponse.setPassword(userAuthentication.getPassword());


            return postUserAuthenticationResponse;
        }
        
        
        
    }


    public List<GetAllUserAuthenticationResponse> getAllUsers() {

        List<GetAllUserAuthenticationResponse> allUsers = new ArrayList<>();

        for (var user : userAuthenticationRepository.findAll()){
            GetAllUserAuthenticationResponse newUser = new GetAllUserAuthenticationResponse(user.getUserId(), user.getName(), user.getSurname(), user.getEmail(), user.getGsm());
            allUsers.add(newUser);
        }
        return allUsers;
    }

    
}

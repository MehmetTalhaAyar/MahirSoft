package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;


@Service
public class UserAuthenticationService {

    UserAuthenticationRepository userAuthenticationRepository;

    PasswordEncoder passwordEncoder;
    
    UserAuthenticationService (UserAuthenticationRepository userAuthenticationRepository,PasswordEncoder passwordEncoder) {
        this.userAuthenticationRepository =  userAuthenticationRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void save(UserAuthentication userAuthentication){

        userAuthentication.setPassword(passwordEncoder.encode(userAuthentication.getPassword()));

        userAuthenticationRepository.save(userAuthentication);
       

    }

    public UserAuthentication findByEmail(String email){
        return userAuthenticationRepository.findByEmail(email);
    }

    public UserAuthentication getUserInfo(PostUserAuthenticationRequest PostUserAuthenticationRequest){
        
        UserAuthentication userAuthentication = userAuthenticationRepository.findByEmail(PostUserAuthenticationRequest.getEmail());
        if (userAuthentication == null) return null;

        if(!passwordEncoder.matches(PostUserAuthenticationRequest.getPassword(), userAuthentication.getPassword())) return null;

        return userAuthentication;

                
    }


    public List<UserAuthentication> getAllUsers() {

        
        return userAuthenticationRepository.findAll();
    }

    public UserAuthentication findById(long id){
        return userAuthenticationRepository.findById(id);
    }

    
}

package com.mahirsoft.webservice.Business.concretes;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.UserRepository;
import com.mahirsoft.webservice.DataAccess.UserRoleRepository;
import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Requests.PostUserRequest;

@Service
public class AuthService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private UserRoleRepository userRoleRepository;


    public User register(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedById(user); // kayıt olan kullanıcı kendisini oluşturmuş oluyor

        var userRole = userRoleRepository.findById(1); // 1 new user without company

        if(userRole == null) return null;

        user.setUserRoleId(userRole);


        return userRepository.save(user);
    
    }

    public User logIn(PostUserRequest postUserRequest){
        
        User user = userRepository.findByEmail(postUserRequest.getEmail()).orElse(null);

        if(user == null) return null;

        if(!passwordEncoder.matches(postUserRequest.getPassword(), user.getPassword())) return null;

        return user;

                
    }
}

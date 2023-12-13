package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.UserAuthenticationService;
import com.mahirsoft.webservice.Entities.UserAuthentication;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/userauthentication")
public class UserAuthenticationController {

    UserAuthenticationService userAuthenticationService;

    UserAuthenticationController (UserAuthenticationService userAuthenticationService){
        this.userAuthenticationService = userAuthenticationService;
    }

    

    @PostMapping("/add")
    ResponseEntity<String> createUser(@Valid @RequestBody UserAuthentication userAuthentication){
        String body = "User created";
        userAuthenticationService.save(userAuthentication);
        return new ResponseEntity<String>(body,HttpStatusCode.valueOf(200));
    }

    @PostMapping("/user")
    ResponseEntity<?> getUserInformation(@Valid @RequestBody UserAuthentication userAuthentication){
        var user = userAuthenticationService.getUserInfo(userAuthentication);

        if (user == null){
            return new ResponseEntity<String>("User not found", HttpStatusCode.valueOf(204));
        }
        
        return new ResponseEntity<UserAuthentication>( user, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/users")
    List<UserAuthentication> getUsers(){
        return userAuthenticationService.getAllUsers();
        
    }
    
}

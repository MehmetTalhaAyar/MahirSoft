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
import com.mahirsoft.webservice.Entities.Requests.CreateUserAuthtenticationRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;
import com.mahirsoft.webservice.Entities.Response.GetAllUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.PostUserAuthenticationResponse;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/userauthentication")
public class UserAuthenticationController {

    UserAuthenticationService userAuthenticationService;

    UserAuthenticationController (UserAuthenticationService userAuthenticationService){
        this.userAuthenticationService = userAuthenticationService;
    }

    

    @PostMapping("/add")
    ResponseEntity<String> createUser(@Valid @RequestBody CreateUserAuthtenticationRequest createUserAuthtenticationRequest){
        String body = "User created";
        userAuthenticationService.save(createUserAuthtenticationRequest.toUserAuthentication());
        return new ResponseEntity<String>(body,HttpStatusCode.valueOf(200));
    }

    @PostMapping("/user")
    ResponseEntity<?> getUserInformation(@Valid @RequestBody PostUserAuthenticationRequest postUserAuthenticationResponse){
        var user = userAuthenticationService.getUserInfo(postUserAuthenticationResponse);

        if (user == null){
            return new ResponseEntity<String>("User not found", HttpStatusCode.valueOf(204));
        }
        
        return new ResponseEntity<PostUserAuthenticationResponse>( user, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/users")
    List<GetAllUserAuthenticationResponse> getUsers(){
        return userAuthenticationService.getAllUsers();
        
    }

    

   
}

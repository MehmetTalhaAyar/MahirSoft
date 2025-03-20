package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.abstracts.TokenService;
import com.mahirsoft.webservice.Business.concretes.AuthService;
import com.mahirsoft.webservice.Entities.Requests.CreateUserRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUserRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralUserResponse;
import com.mahirsoft.webservice.Entities.Response.PostUserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private AuthService authService;

    private TokenService tokenService;


    
    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        String body = "User created";

        var user = authService.register(createUserRequest.toUserAuthentication());

        if(user == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400)); 


        return new ResponseEntity<String>(body,HttpStatusCode.valueOf(201)); // 201
    }

    @PostMapping("/login")
    ResponseEntity<?> getUserInformation(@Valid @RequestBody PostUserRequest postUserRequest){
        var user = authService.logIn(postUserRequest);
        
        if (user == null){
            return new ResponseEntity<String>("User not found", HttpStatusCode.valueOf(204));
        }

        // response nesnesine mapping
        PostUserResponse userResponse = new PostUserResponse();
        
        GeneralUserResponse generalUserResponse = user.toGeneralUserAuthenticationResponse();
         
        var token = tokenService.createToken(postUserRequest);

        userResponse.setUser(generalUserResponse);
        userResponse.setToken(token);
        
    
        return new ResponseEntity<PostUserResponse>( userResponse, HttpStatusCode.valueOf(200));
    }
}

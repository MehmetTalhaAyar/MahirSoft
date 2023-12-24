package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.ArrayList;
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

        // response nesnesine mapping
        PostUserAuthenticationResponse UserAuthenticationResponse = new PostUserAuthenticationResponse();
        UserAuthenticationResponse.setUserId(user.getUserId());
        UserAuthenticationResponse.setEmail(user.getEmail());
        UserAuthenticationResponse.setPassword(user.getPassword());
    
        return new ResponseEntity<PostUserAuthenticationResponse>( UserAuthenticationResponse, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/users")
    List<GetAllUserAuthenticationResponse> getUsers(){


        var items = userAuthenticationService.getAllUsers();
        List<GetAllUserAuthenticationResponse> allUsers = new ArrayList<>();

        for (var user : items){
            GetAllUserAuthenticationResponse newUser = new GetAllUserAuthenticationResponse();
            newUser.setUserId(user.getUserId());
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setEmail(user.getEmail());
            newUser.setGsm(user.getGsm());
            newUser.setTasks(user.getResponsibleTasks());
            allUsers.add(newUser);
        }

        return allUsers;
        
    }

    

   
}

package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.abstracts.TokenService;
import com.mahirsoft.webservice.Business.concretes.UserAuthenticationService;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.CreateUserAuthtenticationRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.GetAllUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.PostUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.TaskCountResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/userauthentication")
public class UserAuthenticationController {

    UserAuthenticationService userAuthenticationService;

    TokenService tokenService;

    UserAuthenticationController (UserAuthenticationService userAuthenticationService,TokenService tokenService){
        this.userAuthenticationService = userAuthenticationService;
        this.tokenService = tokenService;
    }

    

    @PostMapping("/add")
    ResponseEntity<String> createUser(@Valid @RequestBody CreateUserAuthtenticationRequest createUserAuthtenticationRequest){
        String body = "User created";

        userAuthenticationService.save(createUserAuthtenticationRequest.toUserAuthentication());
        return new ResponseEntity<String>(body,HttpStatusCode.valueOf(200));
    }

    @PostMapping("/user")
    ResponseEntity<?> getUserInformation(@Valid @RequestBody PostUserAuthenticationRequest postUserAuthenticationRequest){
        var user = userAuthenticationService.getUserInfo(postUserAuthenticationRequest);

        if (user == null){
            return new ResponseEntity<String>("User not found", HttpStatusCode.valueOf(204));
        }

        // response nesnesine mapping
        PostUserAuthenticationResponse userAuthenticationResponse = new PostUserAuthenticationResponse();
        
        GeneralUserAuthenticationResponse generalUserAuthenticationResponse = user.toGeneralUserAuthenticationResponse();
         
        var token = tokenService.createToken(postUserAuthenticationRequest);

        userAuthenticationResponse.setUser(generalUserAuthenticationResponse);
        userAuthenticationResponse.setToken(token);
        
    
        return new ResponseEntity<PostUserAuthenticationResponse>( userAuthenticationResponse, HttpStatusCode.valueOf(200));
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

    @GetMapping("/{id}")
    public UserAuthentication getuser(@PathVariable long id){

        var user = userAuthenticationService.findById(id);
        if(user == null) return null;


        return user;
    }

    @GetMapping("/taskcount")
    public ResponseEntity<?> getTaskCount(@AuthenticationPrincipal DefaultUser currentUser){
        
        var user = userAuthenticationService.findById(currentUser.getId());

        if(user == null) return new ResponseEntity<String>("Something went Wrong", HttpStatusCode.valueOf(400));

        return new ResponseEntity<TaskCountResponse>(user.toTaskCountResponse(), HttpStatusCode.valueOf(200));
    }

    

   
}

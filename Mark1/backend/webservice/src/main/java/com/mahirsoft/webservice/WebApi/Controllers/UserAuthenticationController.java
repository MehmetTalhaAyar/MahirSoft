package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.UserAuthenticationService;
import com.mahirsoft.webservice.Entities.ResponseMessage;
import com.mahirsoft.webservice.Entities.UserAuthentication;


@RestController
@RequestMapping("/api/v1/userauthentication")
public class UserAuthenticationController {

    UserAuthenticationService userAuthenticationService;

    UserAuthenticationController (UserAuthenticationService userAuthenticationService){
        this.userAuthenticationService = userAuthenticationService;
    }

    

    @PostMapping("/add")
    ResponseMessage createUser(@RequestBody UserAuthentication userAuthentication){

        userAuthenticationService.save(userAuthentication);
        return new ResponseMessage("User created!");
    }

    @PostMapping("/user")
    UserAuthentication getUserInformation(@RequestBody UserAuthentication userAuthentication){
        
        
        return userAuthenticationService.getUserInfo(userAuthentication);
    }

    @GetMapping("/users")
    List<UserAuthentication> getUsers(){
        return userAuthenticationService.getAllUsers();
        
    }
    
}

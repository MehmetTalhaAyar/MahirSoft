package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.UserAuthenticationService;
import com.mahirsoft.webservice.Entities.Errors.ApiError;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleMethodArgutmentNotValidException(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        //path değiştirilecek
        apiError.setPath("/api");
        apiError.setMessage("Validation error");
        apiError.setStatus(400);
        var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage,(existing,replacing) -> existing));

        apiError.setValidationErrors(validationErrors);
        return new ResponseEntity<ApiError>(apiError, HttpStatusCode.valueOf(apiError.getStatus()));
    }

   
}

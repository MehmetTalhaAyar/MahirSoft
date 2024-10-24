package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.abstracts.TokenService;
import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.UserAuthenticationService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Requests.CreateUserAuthtenticationRequest;
import com.mahirsoft.webservice.Entities.Requests.PostImageUpdateRequest;
import com.mahirsoft.webservice.Entities.Requests.PostUserAuthenticationRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.GetAllUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.PostUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.PutImageResponse;
import com.mahirsoft.webservice.Entities.Response.TaskCountResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/userauthentication")
public class UserAuthenticationController {

    UserAuthenticationService userAuthenticationService;

    PermissionService permissionService;

    TokenService tokenService;


    public UserAuthenticationController(UserAuthenticationService userAuthenticationService,
            PermissionService permissionService, TokenService tokenService) {
        this.userAuthenticationService = userAuthenticationService;
        this.permissionService = permissionService;
        this.tokenService = tokenService;
    }

    @PostMapping("/add")
    ResponseEntity<String> createUser(@Valid @RequestBody CreateUserAuthtenticationRequest createUserAuthtenticationRequest){
        String body = "User created";

        var user = userAuthenticationService.save(createUserAuthtenticationRequest.toUserAuthentication());

        if(user == null) return new ResponseEntity<>(HttpStatusCode.valueOf(400)); 


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

    @GetMapping("/users") // bu endpoint e sadece superadmin ulaşabilir
    List<GetAllUserAuthenticationResponse> getUsers(@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN); 


        var items = userAuthenticationService.getAllUsers();
        List<GetAllUserAuthenticationResponse> allUsers = new ArrayList<>();

        for (var user : items){
            GetAllUserAuthenticationResponse newUser = new GetAllUserAuthenticationResponse();
            newUser.setUserId(user.getUserId());
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setEmail(user.getEmail());
            newUser.setGsm(user.getGsm());
            allUsers.add(newUser);
        }

        return allUsers;
        
    }

    @GetMapping("/{id}")
    public UserAuthentication getuser(@PathVariable long id,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN); 
        var user = userAuthenticationService.findById(id);
        if(user == null) return null;


        return user;
    }

    @GetMapping("/taskcount") // buraya istek atan üzerinden dönüş yapıldığı için yetki kontrolu yok
    public ResponseEntity<?> getTaskCount(@AuthenticationPrincipal DefaultUser currentUser){
        
        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.ANY_AUTHORIZATION);

        return new ResponseEntity<TaskCountResponse>(user.toTaskCountResponse(), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/updateimage") //istek atan üzerinden cevap veriliyor.
    public ResponseEntity<?> handleUpdateImage( @Valid @RequestBody PostImageUpdateRequest postImageUpdateRequest,@AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.ANY_AUTHORIZATION); 

        var image = userAuthenticationService.updateUserImage(user,postImageUpdateRequest);

        PutImageResponse putImageResponse = new PutImageResponse();
        putImageResponse.setImage(image);

        return new ResponseEntity<PutImageResponse>(putImageResponse,HttpStatusCode.valueOf(200));

    }

    

   
}

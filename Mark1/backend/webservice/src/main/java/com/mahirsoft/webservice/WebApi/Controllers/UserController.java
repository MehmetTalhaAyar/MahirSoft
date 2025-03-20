package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.UserService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Requests.PostImageUpdateRequest;
import com.mahirsoft.webservice.Entities.Response.GetAllUserResponse;
import com.mahirsoft.webservice.Entities.Response.PutImageResponse;
import com.mahirsoft.webservice.Entities.Response.TaskCountResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    private PermissionService permissionService;


    public UserController(UserService userService,
            PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    

    @GetMapping("/users") // bu endpoint e sadece superadmin ulaşabilir
    List<GetAllUserResponse> getUsers(@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN); 


        var items = userService.getAllUsers();
        List<GetAllUserResponse> allUsers = new ArrayList<>();

        for (var user : items){
            GetAllUserResponse newUser = new GetAllUserResponse();
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
    public User getuser(@PathVariable long id,@AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN); 
        
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

        var image = userService.updateUserImage(user,postImageUpdateRequest);

        PutImageResponse putImageResponse = new PutImageResponse();
        putImageResponse.setImage(image);

        return new ResponseEntity<PutImageResponse>(putImageResponse,HttpStatusCode.valueOf(200));

    }

    

   
}

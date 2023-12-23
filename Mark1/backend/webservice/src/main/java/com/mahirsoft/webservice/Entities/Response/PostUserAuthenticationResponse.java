package com.mahirsoft.webservice.Entities.Response;

import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public class PostUserAuthenticationResponse {

    private long userId;

    private String email;
        
    private String password;


    public UserAuthentication toUserAuthentication() {
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setEmail(email);
        userAuthentication.setPassword(password);
        return userAuthentication;
    }

    //getter Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

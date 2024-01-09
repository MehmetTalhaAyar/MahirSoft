package com.mahirsoft.webservice.Entities.Response;

import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public class PostUserAuthenticationResponse {

    private long userId;

    private String email;
        
    private String name;

    private String surname;

    private String fullName;


    public UserAuthentication toUserAuthentication() {
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setEmail(email);
        userAuthentication.setName(name);
        userAuthentication.setSurname(surname);
        
        return userAuthentication;
    }

    //getter Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

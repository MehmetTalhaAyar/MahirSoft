package com.mahirsoft.webservice.Entities.GetModels;

import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public class GetUserAuthentication {

    private String email;
        
    private String password;

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

    public UserAuthentication toUserAuthentication() {
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setEmail(email);
        userAuthentication.setPassword(password);
        return userAuthentication;
    }
}

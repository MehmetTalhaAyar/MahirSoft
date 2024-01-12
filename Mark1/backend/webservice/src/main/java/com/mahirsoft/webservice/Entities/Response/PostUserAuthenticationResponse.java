package com.mahirsoft.webservice.Entities.Response;

import com.mahirsoft.webservice.Entities.Models.Token;


public class PostUserAuthenticationResponse {

    GeneralUserAuthenticationResponse user;

    Token token;

   

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public GeneralUserAuthenticationResponse getUser() {
        return user;
    }

    public void setUser(GeneralUserAuthenticationResponse user) {
        this.user = user;
    }


   
}

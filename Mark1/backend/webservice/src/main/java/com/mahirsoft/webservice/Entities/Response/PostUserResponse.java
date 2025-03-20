package com.mahirsoft.webservice.Entities.Response;

import com.mahirsoft.webservice.Entities.Models.Token;


public class PostUserResponse {

    GeneralUserResponse user;

    Token token;

   

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public GeneralUserResponse getUser() {
        return user;
    }

    public void setUser(GeneralUserResponse user) {
        this.user = user;
    }


   
}

package com.mahirsoft.webservice.Entities.Response;

public class GetAllUserAuthenticationResponse {

    long userId;

    String name;

    String surname;

    String email;

    String gsm;

    public GetAllUserAuthenticationResponse(){
        
    }

    public GetAllUserAuthenticationResponse(long userId,String name,String surname,String email,String gsm){
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gsm = gsm;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }
    
}

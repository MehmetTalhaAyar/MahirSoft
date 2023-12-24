package com.mahirsoft.webservice.Entities.Response;

import java.util.List;

import com.mahirsoft.webservice.Entities.Models.Task;

public class GetAllUserAuthenticationResponse {

    long userId;

    String name;

    String surname;

    String email;

    String gsm;

    List<Task> tasks;

    public GetAllUserAuthenticationResponse(){
        
    }

    public GetAllUserAuthenticationResponse(long userId,String name,String surname,String email,String gsm,List<Task> tasks){
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gsm = gsm;
        this.tasks = tasks;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
}

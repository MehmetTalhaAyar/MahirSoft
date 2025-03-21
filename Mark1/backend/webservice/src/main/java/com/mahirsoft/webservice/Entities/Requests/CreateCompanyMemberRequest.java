package com.mahirsoft.webservice.Entities.Requests;

import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Validations.Annotations.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateCompanyMemberRequest {

    @NotBlank(message = "E-mail cannot be empty!")
    @UniqueEmail
    @Email(message = "Please enter a valid email format.")
    private String email;

    @NotBlank
    private String title;

    @NotBlank(message = "name cannot be empty!")
    private String name;
    @NotBlank(message = "surname cannot be empty!")
    private String surname;

    @NotBlank(message = "gsm cannot be empty!")
    @Pattern(regexp = "^[0-9]+$",message = "Please enter only numerical characters.")
    private String gsm;

    @NotBlank(message = "Passoword cannot be empty!")
    @Size(min = 8,max = 32,message = "Password should be between 8 and 32 characters.") 
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$",message = "Your password must contain at least one uppercase letter, one lowercase letter, and one digit.")
    private String password;


    public User toUserAuthentication(){
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setSurname(surname);
        newUser.setTitle(title);
        newUser.setName(name);
        newUser.setGsm(gsm);

        return newUser;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}

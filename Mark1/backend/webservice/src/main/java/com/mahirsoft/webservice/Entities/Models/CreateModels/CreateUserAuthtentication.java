package com.mahirsoft.webservice.Entities.Models.CreateModels;

import com.mahirsoft.webservice.Entities.Validations.Annotations.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateUserAuthtentication {

    @NotBlank(message = "E-mail cannot be empty!")
    @UniqueEmail
    @Email
    private String email;
    
    @NotBlank(message = "Passoword cannot be empty!")
    @Size(min = 8,max = 32,message = "Password should be between 8 and 32 characters.") 
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$",message = "Your password must contain at least one uppercase letter, one lowercase letter, and one digit.")
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
}

package com.mahirsoft.webservice.Entities.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="UserAuthentication",uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class UserAuthentication {
    
    @Id
    @GeneratedValue
    @Column(name ="userId")
    private long userId;

    @Email
    @Column(name = "email")
    private String email;
    
    @NotBlank
    @Size(min = 8,max = 32,message = "Password should be between 8 and 32 characters.") 
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$",message = "Your password must contain at least one uppercase letter, one lowercase letter, and one digit.")
    @Column(name = "password")
    private String password;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

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

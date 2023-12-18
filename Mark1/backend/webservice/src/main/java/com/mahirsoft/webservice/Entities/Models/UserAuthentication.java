package com.mahirsoft.webservice.Entities.Models;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;



@Entity
@Table(name="UserAuthentication",uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class UserAuthentication {
    
    @Id
    @GeneratedValue
    @Column(name ="userId")
    private long userId;

    @Column(name = "email")
    private String email;
    
    
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

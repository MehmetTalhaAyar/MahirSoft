package com.mahirsoft.webservice.Entities.Models;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Table(name = "tokens")
@Entity
public class Token {

    @Transient
    private String prefix;

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "createdOn")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name = "expiredOn")
    private LocalDateTime expiredOn = LocalDateTime.now().plusMonths(1);

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    

    public Token() {
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(LocalDateTime expiredOn) {
        this.expiredOn = expiredOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}

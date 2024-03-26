package com.mahirsoft.webservice.Entities.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserRoleAuthorizations")
public class UserRoleAuthorization {

    @Id
    @GeneratedValue
    @Column(name = "userRoleAuthorizationId")
    private long userRoleAuthorizationId;

    @ManyToOne
    @JoinColumn(name = "userRole",referencedColumnName = "userRoleId")
    private UserRole userRoleId;

    @ManyToOne
    @JoinColumn(name = "authorizationId",referencedColumnName = "authorizationId")
    private Authorization authorizationId;

    public long getUserRoleAuthorizationId() {
        return userRoleAuthorizationId;
    }

    public void setUserRoleAuthorizationId(long userRoleAuthorizationId) {
        this.userRoleAuthorizationId = userRoleAuthorizationId;
    }

    public UserRole getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UserRole userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Authorization getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(Authorization authorizationId) {
        this.authorizationId = authorizationId;
    }

   
}

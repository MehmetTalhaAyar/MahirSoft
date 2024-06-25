package com.mahirsoft.webservice.Entities.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Authorizations")
public class Authorization {

    @Id
    @Column(name = "authorizationId")
    private long authorizationId;

    @Column(name = "definition")
    private String definition;

    @JsonIgnore
    @OneToMany(mappedBy = "authorizationId")
    private List<UserRoleAuthorization> userRoleAuthorizations;

    public long getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(long authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<UserRoleAuthorization> getUserRoleAuthorizations() {
        return userRoleAuthorizations;
    }

    public void setUserRoleAuthorizations(List<UserRoleAuthorization> userRoleAuthorizations) {
        this.userRoleAuthorizations = userRoleAuthorizations;
    }


    
}

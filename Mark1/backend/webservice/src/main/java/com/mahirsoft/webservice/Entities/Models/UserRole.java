package com.mahirsoft.webservice.Entities.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.GeneralUserRoleResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserRoles")
public class UserRole {

    @Id
    @GeneratedValue
    @Column(name = "userRoleId")
    private long userRoleId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "companyId",referencedColumnName = "companyId")
    private Company companyId;

    @JsonIgnore
    @OneToMany(mappedBy = "userRoleId")
    private List<UserAuthentication> users;

    @JsonIgnore
    @OneToMany(mappedBy = "userRoleId")
    private List<UserRoleAuthorization> userRoleAuthorizations;

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public List<UserAuthentication> getUsers() {
        return users;
    }

    public void setUsers(List<UserAuthentication> users) {
        this.users = users;
    }

    public List<UserRoleAuthorization> getUserRoleAuthorizations() {
        return userRoleAuthorizations;
    }

    public void setUserRoleAuthorizations(List<UserRoleAuthorization> userRoleAuthorizations) {
        this.userRoleAuthorizations = userRoleAuthorizations;
    }

    public GeneralUserRoleResponse toGeneralUserRoleResponse(){
        GeneralUserRoleResponse generalUserRoleResponse = new GeneralUserRoleResponse();
        generalUserRoleResponse.setName(name);
        generalUserRoleResponse.setUserRoleId(userRoleId);
        return generalUserRoleResponse;
    }

}

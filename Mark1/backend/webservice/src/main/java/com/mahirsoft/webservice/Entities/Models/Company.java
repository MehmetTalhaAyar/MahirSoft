package com.mahirsoft.webservice.Entities.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Companies")
public class Company {

    @Id
    @GeneratedValue
    @Column(name = "companyId")
    private long companyId;

    @Column(name = "description")
    private String description;

    @Column(name = "deletionStateCode")
    private int deletionStateCode = 0;

    @ManyToOne
    @JoinColumn(name = "managerId",referencedColumnName = "userId")
    private UserAuthentication managerId;

    @JsonIgnore
    @OneToMany(mappedBy = "companyId")
    List<Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "companyId")
    List<UserAuthentication> companyMembers;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
    }

    public UserAuthentication getManagerId() {
        return managerId;
    }

    public void setManagerId(UserAuthentication managerId) {
        this.managerId = managerId;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<UserAuthentication> getCompanyMembers() {
        return companyMembers;
    }

    public void setCompanyMembers(List<UserAuthentication> companyMembers) {
        this.companyMembers = companyMembers;
    }

    
    
}

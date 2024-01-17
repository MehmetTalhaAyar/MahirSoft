package com.mahirsoft.webservice.Entities.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.CompanyResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;

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

    @Column(name = "name")
    private String name;

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

    public CompanyResponse toCompanyResponse(){
        CompanyResponse companyResponse = new CompanyResponse();

        companyResponse.setId(companyId);
        companyResponse.setName(name);

        return companyResponse;
    }

    public List<GeneralUserAuthenticationResponse> toListGeneralUserAuthenticationResponses(){
        List<GeneralUserAuthenticationResponse> members = new ArrayList<>();

        for(var eleman : companyMembers){
            GeneralUserAuthenticationResponse user = new GeneralUserAuthenticationResponse();

            user.setCompany(toCompanyResponse());
            user.setEmail(eleman.getEmail());
            user.setFullName(eleman.getName() + " " +eleman.getSurname());
            user.setGsm(eleman.getGsm());
            user.setName(eleman.getName());
            user.setSurname(eleman.getSurname());
            user.setUserId(eleman.getUserId());

            members.add(user);

        }

        return members;
    }

    public List<GeneralProjectResponse> toGeneralProjectResponses(){
        List<GeneralProjectResponse> projectList = new ArrayList<>();

        for(var eleman : projects){
            GeneralProjectResponse currentProject = new GeneralProjectResponse();

            currentProject.setCreatedOn(eleman.getCreatedOn());
            currentProject.setLeadingPerson(eleman.getLeadingPersonId().toGeneralUserAuthenticationResponse());
            currentProject.setName(eleman.getName());
            currentProject.setStages(eleman.toGeneralStageResponse());

            projectList.add(currentProject);
        }
        return projectList;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
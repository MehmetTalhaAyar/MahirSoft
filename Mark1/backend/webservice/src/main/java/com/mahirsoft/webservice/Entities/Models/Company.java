package com.mahirsoft.webservice.Entities.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.CompanyResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralCompanyResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralUserResponse;
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
    private User managerId;

    @JsonIgnore
    @OneToMany(mappedBy = "companyId")
    List<Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "companyId")
    List<User> companyMembers;

    @JsonIgnore
    @OneToMany(mappedBy = "companyId")
    List<UserRole> userRoles;

    @JsonIgnore
    @OneToMany(mappedBy = "companyId")
    List<CompanyInvitation> invitationsList;



    public CompanyResponse toCompanyResponse(){
        CompanyResponse companyResponse = new CompanyResponse();

        companyResponse.setId(companyId);
        companyResponse.setName(name);
        companyResponse.setManagerId(managerId.getUserId());

        return companyResponse;
    }

    public List<GeneralUserResponse> toListGeneralUserAuthenticationResponses(){
        List<GeneralUserResponse> members = new ArrayList<>();

        for(var eleman : companyMembers){
            GeneralUserResponse user = new GeneralUserResponse();

            user.setCompany(toCompanyResponse());
            user.setEmail(eleman.getEmail());
            user.setFullName(eleman.getName() + " " +eleman.getSurname());
            user.setGsm(eleman.getGsm());
            user.setName(eleman.getName());
            user.setSurname(eleman.getSurname());
            user.setUserId(eleman.getUserId());
            user.setTitle(eleman.getTitle());
            user.setImage(eleman.getImage());

            members.add(user);

        }

        return members;
    }


    public GeneralCompanyResponse toGeneralCompanyResponse(){
        GeneralCompanyResponse generalCompanyResponse = new GeneralCompanyResponse();
        generalCompanyResponse.setDescription(description);
        generalCompanyResponse.setId(companyId);
        generalCompanyResponse.setManager(managerId.toGeneralUserAuthenticationResponse());
        generalCompanyResponse.setName(name);

        return generalCompanyResponse;
    }

    public List<GeneralUserRoleResponse> toGeneralUserRoleResponse(){

        List<GeneralUserRoleResponse> generalUserRoles = new ArrayList<>();
        for(var eleman : userRoles){
            GeneralUserRoleResponse generalUserRoleResponse = new GeneralUserRoleResponse();
            generalUserRoleResponse.setName(eleman.getName());
            generalUserRoleResponse.setUserRoleId(eleman.getUserRoleId());

            generalUserRoles.add(generalUserRoleResponse);
        }
        
        return generalUserRoles;
    }

    public List<GeneralProjectResponse> toGeneralProjectResponses(){
        List<GeneralProjectResponse> projectList = new ArrayList<>();

        for(var eleman : projects){
            if(eleman.getDeletionStateCode() == 1) continue;

            GeneralProjectResponse currentProject = new GeneralProjectResponse();

            currentProject.setId(eleman.getProjectId());
            currentProject.setCreatedOn(eleman.getCreatedOn());
            currentProject.setLeadingPerson(eleman.getLeadingPersonId().toGeneralUserAuthenticationResponse());
            currentProject.setName(eleman.getName());
            currentProject.setStages(eleman.toGeneralStageResponses());
            currentProject.setMembers(eleman.toGeneralUserAuthenticationResponses());

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

    public User getManagerId() {
        return managerId;
    }

    public void setManagerId(User managerId) {
        this.managerId = managerId;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<User> getCompanyMembers() {
        return companyMembers;
    }

    public void setCompanyMembers(List<User> companyMembers) {
        this.companyMembers = companyMembers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    
    
}

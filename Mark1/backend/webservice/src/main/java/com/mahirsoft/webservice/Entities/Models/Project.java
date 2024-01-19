package com.mahirsoft.webservice.Entities.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralStageResponse;
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
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "projectId")
    private long projectId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "leadingPersonId", referencedColumnName = "userId")
    private UserAuthentication leadingPersonId;

    @Column(name = "deletionStateCode")
    private int deletionStateCode = 0;

    @Column(name = "createdOn")
    private LocalDateTime createdOn = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "companyId",referencedColumnName = "companyId")
    private Company companyId;

    @JsonIgnore
    @OneToMany(mappedBy = "projectId")
    private List<Stage> stages;

    @JsonIgnore
    @OneToMany(mappedBy = "projectId")
    private List<ProjectUser> projectMembers;

    public Project() {
    }

    public GeneralProjectResponse toGeneralProjectResponse(){
        GeneralUserAuthenticationResponse leadPerson = toLeadPerson();

        GeneralProjectResponse response = new GeneralProjectResponse();
        response.setId(projectId);
        response.setCreatedOn(createdOn);
        response.setName(name);
        response.setLeadingPerson(leadPerson);
        response.setStages(toGeneralStageResponse());

        return response;

    }

    public List<GeneralStageResponse> toGeneralStageResponse(){
        List<GeneralStageResponse> stageResponse = new ArrayList<>();
        for(var eleman : stages){
            GeneralStageResponse generalStageResponse = new GeneralStageResponse();
            generalStageResponse.setId(eleman.getStageId());
            generalStageResponse.setName(eleman.getName());

            stageResponse.add(generalStageResponse);
        }

        return stageResponse;
    }

    public GeneralUserAuthenticationResponse toLeadPerson(){
        GeneralUserAuthenticationResponse leadPerson = new GeneralUserAuthenticationResponse();
        leadPerson.setEmail(leadingPersonId.getEmail());
        leadPerson.setGsm(leadingPersonId.getGsm());
        leadPerson.setName(leadingPersonId.getName());
        leadPerson.setSurname(leadingPersonId.getSurname());
        leadPerson.setUserId(leadingPersonId.getUserId());
        leadPerson.setCompany(leadingPersonId.getCompanyId().toCompanyResponse());
        leadPerson.setFullName(leadingPersonId.getName()+ " " + leadingPersonId.getSurname());


        return leadPerson;

    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAuthentication getLeadingPersonId() {
        return leadingPersonId;
    }

    public void setLeadingPersonId(UserAuthentication leadingPersonId) {
        this.leadingPersonId = leadingPersonId;
    }

    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public List<ProjectUser> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(List<ProjectUser> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

}

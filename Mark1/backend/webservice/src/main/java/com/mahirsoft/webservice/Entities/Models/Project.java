package com.mahirsoft.webservice.Entities.Models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Date createdOn = Date.valueOf(LocalDate.now());

    @ManyToOne
    @JoinColumn(name = "companyId",referencedColumnName = "companyId")
    private Company companyId;

    @JsonIgnore
    @OneToMany(mappedBy = "projectId")
    private List<Task> tasks;

    @JsonIgnore
    @OneToMany(mappedBy = "projectId")
    private List<Stage> stages;

    public Project() {
    }

    public GeneralProjectResponse toGeneralProjectResponse(){
        GeneralUserAuthenticationResponse leadPerson = toLeadPerson();

        GeneralProjectResponse response = new GeneralProjectResponse();
        response.setCreatedOn(createdOn);
        response.setName(name);
        response.setLeadingPerson(leadPerson);

        return response;

    }

    public GeneralUserAuthenticationResponse toLeadPerson(){
        GeneralUserAuthenticationResponse leadPerson = new GeneralUserAuthenticationResponse();
        leadPerson.setEmail(leadingPersonId.getEmail());
        leadPerson.setGsm(leadingPersonId.getGsm());
        leadPerson.setName(leadingPersonId.getName());
        leadPerson.setSurname(leadingPersonId.getSurname());
        leadPerson.setUserId(leadingPersonId.getUserId());

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

}

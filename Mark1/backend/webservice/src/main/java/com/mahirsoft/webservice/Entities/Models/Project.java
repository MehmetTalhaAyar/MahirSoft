package com.mahirsoft.webservice.Entities.Models;

import java.sql.Date;
import java.time.LocalDate;
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
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "projectId")
    long projectId;

    @Column(name = "name")
    String name;

    @ManyToOne
    @JoinColumn(name = "leadingPersonId", referencedColumnName = "userId")
    UserAuthentication leadingPersonId;

    @Column(name = "deletionStateCode")
    int deletionStateCode = 0;

    @Column(name = "createdOn")
    Date createdOn = Date.valueOf(LocalDate.now());

    @Column(name = "companyId")
    long companyId;

    @JsonIgnore
    @OneToMany(mappedBy = "projectId")
    List<Task> tasks;

    @JsonIgnore
    @OneToMany(mappedBy = "projectId")
    List<Stage> stages;

    public Project() {
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

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public UserAuthentication getLeadingPersonId() {
        return leadingPersonId;
    }

    public void setLeadingPersonId(UserAuthentication leadingPersonId) {
        this.leadingPersonId = leadingPersonId;
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

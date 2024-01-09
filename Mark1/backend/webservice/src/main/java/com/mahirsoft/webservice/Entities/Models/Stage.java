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
@Table(name = "Stages")
public class Stage {

    @Id
    @GeneratedValue
    @Column(name = "stageId")
    long stageId;

    @Column(name = "name")
    String name;

    @ManyToOne
    @JoinColumn(name = "createdById" ,referencedColumnName = "userId")
    UserAuthentication createdById;

    @Column(name = "createdOn")
    Date createdOn = Date.valueOf(LocalDate.now());

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    Project projectId;

    @Column(name = "deletionStateCode")
    int deletionStateCode = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "stageId")
    List<Task> tasks;

    public long getStageId() {
        return stageId;
    }

    public void setStageId(long stageId) {
        this.stageId = stageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAuthentication getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UserAuthentication createdById) {
        this.createdById = createdById;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }


    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    
}

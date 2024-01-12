package com.mahirsoft.webservice.Entities.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProjectUser")
public class ProjectUser {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "projectId",referencedColumnName = "projectId")
    private Project projectId;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private UserAuthentication userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public UserAuthentication getUserId() {
        return userId;
    }

    public void setUserId(UserAuthentication userId) {
        this.userId = userId;
    }
    
}

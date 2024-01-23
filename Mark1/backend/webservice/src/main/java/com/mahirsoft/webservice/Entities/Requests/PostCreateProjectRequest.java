package com.mahirsoft.webservice.Entities.Requests;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class PostCreateProjectRequest {

    @Valid
    private CreateProjectRequest project;

    @NotNull
    private long adminId;
    
    private List<Long> projectUserIds;

    public CreateProjectRequest getProject() {
        return project;
    }

    public void setProject(CreateProjectRequest project) {
        this.project = project;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public List<Long> getProjectUserIds() {
        return projectUserIds;
    }

    public void setProjectUserIds(List<Long> projectUserIds) {
        this.projectUserIds = projectUserIds;
    }

}

package com.mahirsoft.webservice.Entities.Response;

import com.mahirsoft.webservice.Entities.Models.Project;

public class PostProjectAndStageResponse {

    private String name;

    private Project projectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }
    
}

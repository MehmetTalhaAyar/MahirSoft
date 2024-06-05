package com.mahirsoft.webservice.Entities.Requests;

public class PostAddMemberToProjectRequest {

    private long projectId;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    
}

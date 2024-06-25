package com.mahirsoft.webservice.Entities.Requests;

public class PostSearchProjectMembersRequest {

    private long projectId;

    private String searchKey;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
    
}

package com.mahirsoft.webservice.Entities.Requests;

public class PostGetStageAndProjectMembersRequest {

    private long stageId;

    private String searchKey;

    public long getStageId() {
        return stageId;
    }

    public void setStageId(long stageId) {
        this.stageId = stageId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

 
}

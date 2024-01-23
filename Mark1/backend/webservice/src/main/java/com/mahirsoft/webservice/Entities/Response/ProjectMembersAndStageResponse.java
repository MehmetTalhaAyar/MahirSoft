package com.mahirsoft.webservice.Entities.Response;

import java.util.List;

public class ProjectMembersAndStageResponse {


    private List<UserAuthenticationResponse> users;

    private List<StageResponse> stages;

    public List<UserAuthenticationResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserAuthenticationResponse> users) {
        this.users = users;
    }

    public List<StageResponse> getStages() {
        return stages;
    }

    public void setStages(List<StageResponse> stages) {
        this.stages = stages;
    }

    
}

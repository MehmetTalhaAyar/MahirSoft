package com.mahirsoft.webservice.Entities.Response;

public class GeneralProjectUserResponse {

    private long id;

    private GeneralProjectResponse project;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public GeneralProjectResponse getProject() {
        return project;
    }

    public void setProject(GeneralProjectResponse project) {
        this.project = project;
    }
    
}

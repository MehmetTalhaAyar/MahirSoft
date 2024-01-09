package com.mahirsoft.webservice.Entities.Response;


public class PostProjectAndStageResponse {

    private String name;

    private GeneralProjectResponse project;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneralProjectResponse getProject() {
        return project;
    }

    public void setProject(GeneralProjectResponse project) {
        this.project = project;
    }

   
    
}

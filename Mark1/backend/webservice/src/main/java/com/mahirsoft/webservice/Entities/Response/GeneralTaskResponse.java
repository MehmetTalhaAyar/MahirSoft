package com.mahirsoft.webservice.Entities.Response;

public class GeneralTaskResponse {

    private long id;

    private String name;

    private String description;

    private GeneralStageResponse stage;

    private GeneralUserAuthenticationResponse responsiblePerson;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeneralStageResponse getStage() {
        return stage;
    }

    public void setStage(GeneralStageResponse stage) {
        this.stage = stage;
    }

    public GeneralUserAuthenticationResponse getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(GeneralUserAuthenticationResponse responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    
}

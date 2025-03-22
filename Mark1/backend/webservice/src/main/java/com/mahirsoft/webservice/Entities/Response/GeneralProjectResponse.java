package com.mahirsoft.webservice.Entities.Response;


import java.time.LocalDateTime;
import java.util.List;



public class GeneralProjectResponse {

    private long id;

    private String name;

    private String description;

    private GeneralUserResponse leadingPerson;

    private LocalDateTime createdOn;

    private List<GeneralStageResponse> stages;

    private List<GeneralUserResponse> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneralUserResponse getLeadingPerson() {
        return leadingPerson;
    }

    public void setLeadingPerson(GeneralUserResponse leadingPerson) {
        this.leadingPerson = leadingPerson;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<GeneralStageResponse> getStages() {
        return stages;
    }

    public void setStages(List<GeneralStageResponse> stages) {
        this.stages = stages;
    }

    public List<GeneralUserResponse> getMembers() {
        return members;
    }

    public void setMembers(List<GeneralUserResponse> members) {
        this.members = members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    
}

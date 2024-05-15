package com.mahirsoft.webservice.Entities.Response;


import java.time.LocalDateTime;
import java.util.List;



public class GeneralProjectResponse {

    private long id;

    private String name;

    private GeneralUserAuthenticationResponse leadingPerson;

    private LocalDateTime createdOn;

    private List<GeneralStageResponse> stages;

    private List<GeneralUserAuthenticationResponse> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneralUserAuthenticationResponse getLeadingPerson() {
        return leadingPerson;
    }

    public void setLeadingPerson(GeneralUserAuthenticationResponse leadingPerson) {
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

    public List<GeneralUserAuthenticationResponse> getMembers() {
        return members;
    }

    public void setMembers(List<GeneralUserAuthenticationResponse> members) {
        this.members = members;
    }


    
}

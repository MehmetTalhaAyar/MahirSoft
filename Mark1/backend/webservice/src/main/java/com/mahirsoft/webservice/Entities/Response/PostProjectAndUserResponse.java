package com.mahirsoft.webservice.Entities.Response;

import java.sql.Date;
import java.util.List;

import com.mahirsoft.webservice.Entities.Models.Stage;

public class PostProjectAndUserResponse {

    private String name;

    private GeneralUserAuthenticationResponse leadingPerson;

    private Date createdOn;

    private List<Stage> stages;

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }
}

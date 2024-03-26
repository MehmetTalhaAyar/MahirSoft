package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotBlank;

public class CreateCompnayRequest {

    private String name;

    @NotBlank
    private String description;

    private long managerId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }
    
}

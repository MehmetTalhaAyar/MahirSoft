package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotBlank;

public class CreateCompnayRequest {

    private String name;

    @NotBlank
    private String description;

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
    
}

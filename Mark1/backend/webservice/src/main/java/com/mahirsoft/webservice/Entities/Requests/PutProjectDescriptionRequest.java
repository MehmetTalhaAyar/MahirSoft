package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotBlank;

public class PutProjectDescriptionRequest {

    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}

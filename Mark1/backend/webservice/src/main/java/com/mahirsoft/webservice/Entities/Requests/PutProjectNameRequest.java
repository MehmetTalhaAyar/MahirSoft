package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotBlank;

public class PutProjectNameRequest {
    
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}

package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateProjectRequest {
    
    @Size(min = 2,max = 32,message = "Size must be between 2 and 32 characters!")
    @NotBlank(message = "Project name can not be null")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

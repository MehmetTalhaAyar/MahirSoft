package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PutUpdateStageNameRequest {

    @NotNull
    private long stageId;

    @Size(max = 32)
    @NotBlank
    private String name;

    public long getStageId() {
        return stageId;
    }

    public void setStageId(long stageId) {
        this.stageId = stageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}

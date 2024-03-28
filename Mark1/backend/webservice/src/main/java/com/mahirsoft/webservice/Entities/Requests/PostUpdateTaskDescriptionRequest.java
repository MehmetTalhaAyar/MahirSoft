package com.mahirsoft.webservice.Entities.Requests;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;

public class PostUpdateTaskDescriptionRequest {

    private long taskId;

    @Lob
    @NotBlank(message = "Task description can not be empty!")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
    
}

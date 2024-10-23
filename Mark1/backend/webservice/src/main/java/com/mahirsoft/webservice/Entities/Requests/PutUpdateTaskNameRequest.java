package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PutUpdateTaskNameRequest {
    
    @NotNull
    private long taskId;

    @Size(min = 4,max = 32)
    private String name;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

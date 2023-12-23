package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTaskRequest {

    @Size(min=2, max=32, message = "Size must be between 2 and 32 characters!")
    @NotBlank(message = "Task name can not be empty!")
    String taskName;

    @NotBlank(message = "Task description can not be empty!")
    String taskDescription;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}

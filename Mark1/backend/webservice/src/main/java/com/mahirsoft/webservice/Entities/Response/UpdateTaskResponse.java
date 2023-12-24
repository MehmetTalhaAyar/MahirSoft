package com.mahirsoft.webservice.Entities.Response;

import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public class UpdateTaskResponse {
    
    String taskName;

    String taskDescription;

    UserAuthentication responsibleId;

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

    public UserAuthentication getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(UserAuthentication responsibleId) {
        this.responsibleId = responsibleId;
    }
}

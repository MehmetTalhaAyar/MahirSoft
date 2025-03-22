package com.mahirsoft.webservice.Entities.Response;

import java.time.LocalDateTime;

public class UpdateTaskResponse {

    private long id;

    private LocalDateTime endTime;
    
    private GeneralUserResponse reporterUser;

    private GeneralUserResponse responsibleId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public GeneralUserResponse getReporterUser() {
        return reporterUser;
    }

    public void setReporterUser(GeneralUserResponse reporterUser) {
        this.reporterUser = reporterUser;
    }

    public GeneralUserResponse getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(GeneralUserResponse responsibleId) {
        this.responsibleId = responsibleId;
    }

    
}

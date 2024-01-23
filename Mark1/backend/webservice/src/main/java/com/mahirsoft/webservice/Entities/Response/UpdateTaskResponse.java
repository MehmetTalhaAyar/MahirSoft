package com.mahirsoft.webservice.Entities.Response;

import java.time.LocalDateTime;

public class UpdateTaskResponse {

    private long id;

    private LocalDateTime endTime;
    
    private GeneralUserAuthenticationResponse reporterUser;

    private GeneralUserAuthenticationResponse responsibleId;

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

    public GeneralUserAuthenticationResponse getReporterUser() {
        return reporterUser;
    }

    public void setReporterUser(GeneralUserAuthenticationResponse reporterUser) {
        this.reporterUser = reporterUser;
    }

    public GeneralUserAuthenticationResponse getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(GeneralUserAuthenticationResponse responsibleId) {
        this.responsibleId = responsibleId;
    }

    
}

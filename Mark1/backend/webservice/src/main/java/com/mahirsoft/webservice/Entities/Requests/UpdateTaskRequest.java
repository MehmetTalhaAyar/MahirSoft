package com.mahirsoft.webservice.Entities.Requests;

import java.time.LocalDateTime;


public class UpdateTaskRequest {
    
    private Long responsibleId;

    private Long reportsToId;

    private LocalDateTime endDate;

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Long getReportsToId() {
        return reportsToId;
    }

    public void setReportsToId(Long reportsToId) {
        this.reportsToId = reportsToId;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    
    
}

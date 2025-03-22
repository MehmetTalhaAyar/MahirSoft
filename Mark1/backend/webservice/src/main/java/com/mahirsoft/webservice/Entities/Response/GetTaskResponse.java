package com.mahirsoft.webservice.Entities.Response;

import java.time.LocalDateTime;
import java.util.List;


public class GetTaskResponse {

    private long taskId;

    private String taskName;

    private String taskDescripton;

    private LocalDateTime createdOn;

    private LocalDateTime taskDeadlineDate;

    private GeneralUserResponse responsibleId;

    private GeneralUserResponse reportsTo;

    private List<GeneralCommentResponse> comments;

    private StageResponse stage;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescripton() {
        return taskDescripton;
    }

    public void setTaskDescripton(String taskDescripton) {
        this.taskDescripton = taskDescripton;
    }

   

 

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getTaskDeadlineDate() {
        return taskDeadlineDate;
    }

    public void setTaskDeadlineDate(LocalDateTime taskDeadlineDate) {
        this.taskDeadlineDate = taskDeadlineDate;
    }



    public List<GeneralCommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<GeneralCommentResponse> comments) {
        this.comments = comments;
    }

    public GeneralUserResponse getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(GeneralUserResponse responsibleId) {
        this.responsibleId = responsibleId;
    }

    public StageResponse getStage() {
        return stage;
    }

    public void setStage(StageResponse stage) {
        this.stage = stage;
    }

    public GeneralUserResponse getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(GeneralUserResponse reportsTo) {
        this.reportsTo = reportsTo;
    }

    
    
}

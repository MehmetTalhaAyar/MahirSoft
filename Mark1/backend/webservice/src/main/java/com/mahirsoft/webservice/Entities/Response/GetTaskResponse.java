package com.mahirsoft.webservice.Entities.Response;

import java.util.Date;

public class GetTaskResponse {

    private long taskId;

    private String taskName;

    private String taskDescripton;

    private Date createdOn;

    private Date taskDeadlineDate;

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getTaskDeadlineDate() {
        return taskDeadlineDate;
    }

    public void setTaskDeadlineDate(Date taskDeadlineDate) {
        this.taskDeadlineDate = taskDeadlineDate;
    }

    
}

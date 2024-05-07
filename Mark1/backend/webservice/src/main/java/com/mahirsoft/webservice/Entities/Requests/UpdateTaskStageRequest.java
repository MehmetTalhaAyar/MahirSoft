package com.mahirsoft.webservice.Entities.Requests;

public class UpdateTaskStageRequest {

    private long taskId;
    
    private long stageId;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getStageId() {
        return stageId;
    }

    public void setStageId(long stageId) {
        this.stageId = stageId;
    }

    
}

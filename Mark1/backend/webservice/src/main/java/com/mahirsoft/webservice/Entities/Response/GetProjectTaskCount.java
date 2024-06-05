package com.mahirsoft.webservice.Entities.Response;

public class GetProjectTaskCount {

    private int totalTaskCount;

    private int finishedTask;

    private int failedTask;

    public int getTotalTaskCount() {
        return totalTaskCount;
    }

    public void setTotalTaskCount(int totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
    }

    public int getFinishedTask() {
        return finishedTask;
    }

    public void setFinishedTask(int finishedTask) {
        this.finishedTask = finishedTask;
    }

    public int getFailedTask() {
        return failedTask;
    }

    public void setFailedTask(int failedTask) {
        this.failedTask = failedTask;
    }
    
}

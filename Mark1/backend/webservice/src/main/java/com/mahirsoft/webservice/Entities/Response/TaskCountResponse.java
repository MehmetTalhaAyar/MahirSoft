package com.mahirsoft.webservice.Entities.Response;

public class TaskCountResponse {

    private int totalTaskCount;

    private int pendingTaskCount;

    private int dueTaskCount;

    public int getTotalTaskCount() {
        return totalTaskCount;
    }

    public void setTotalTaskCount(int totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
    }

    public int getPendingTaskCount() {
        return pendingTaskCount;
    }

    public void setPendingTaskCount(int pendingTaskCount) {
        this.pendingTaskCount = pendingTaskCount;
    }

    public int getDueTaskCount() {
        return dueTaskCount;
    }

    public void setDueTaskCount(int dueTaskCount) {
        this.dueTaskCount = dueTaskCount;
    }


   
    
}

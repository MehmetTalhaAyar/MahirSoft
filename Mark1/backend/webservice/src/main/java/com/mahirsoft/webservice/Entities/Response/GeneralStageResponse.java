package com.mahirsoft.webservice.Entities.Response;

import java.util.List;

public class GeneralStageResponse {

    private long id;

    private String name;

    private List<TaskResponse> tasks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TaskResponse> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResponse> tasks) {
        this.tasks = tasks;
    }

    
}

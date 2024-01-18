package com.mahirsoft.webservice.Entities.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.GeneralStageResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralTaskResponse;
import com.mahirsoft.webservice.Entities.Response.StageResponse;
import com.mahirsoft.webservice.Entities.Response.TaskResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stages")
public class Stage {

    @Id
    @GeneratedValue
    @Column(name = "stageId")
    private long stageId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "createdById" ,referencedColumnName = "userId")
    private UserAuthentication createdById;

    @Column(name = "createdOn")
    private LocalDateTime createdOn = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project projectId;

    @Column(name = "deletionStateCode")
    private int deletionStateCode = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "stageId")
    private List<Task> tasks;


    public GeneralStageResponse toGeneralStageResponse(){
        GeneralStageResponse newstage = new GeneralStageResponse();
        newstage.setId(stageId);
        newstage.setName(name);
        newstage.setTasks(toTaskResponses());

        return newstage;
    }

    public List<GeneralTaskResponse> toGeneralTaskResponses(){
        List<GeneralTaskResponse> taskResponses = new ArrayList<>();

        for(var eleman : tasks){
            GeneralTaskResponse currentTask = new GeneralTaskResponse();
            currentTask.setDescription(eleman.getTaskDescription());
            currentTask.setName(eleman.getTaskName());
            currentTask.setStage(eleman.getStageId().toGeneralStageResponse());
            currentTask.setResponsiblePerson(eleman.getResposibleId().toGeneralUserAuthenticationResponse());

            taskResponses.add(currentTask);

        }
        return taskResponses;
    }

    private List<TaskResponse> toTaskResponses(){
        List<TaskResponse> taskList = new ArrayList<>();

        for(var eleman : tasks){
            TaskResponse currentTask = new TaskResponse();
            currentTask.setId(eleman.getTaskId());
            currentTask.setName(eleman.getTaskName());
            currentTask.setDescription(eleman.getTaskDescription());
            taskList.add(currentTask);
        }

        return taskList;
    }

    public StageResponse toStageResponse(){
        StageResponse stageResponse = new StageResponse();
        stageResponse.setId(stageId);
        stageResponse.setName(name);
        return stageResponse;
    }

    public long getStageId() {
        return stageId;
    }

    public void setStageId(long stageId) {
        this.stageId = stageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAuthentication getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UserAuthentication createdById) {
        this.createdById = createdById;
    }

    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    
}

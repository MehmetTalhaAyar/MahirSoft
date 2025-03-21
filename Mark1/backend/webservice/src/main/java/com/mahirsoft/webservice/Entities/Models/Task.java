package com.mahirsoft.webservice.Entities.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.GeneralCommentResponse;
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
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue
    @Column(name = "taskId")
    private long taskId;
    
    @ManyToOne
    @JoinColumn(name = "responsibleId",referencedColumnName = "userId")
    private User resposibleId;

    @Column(name = "taskName")
    private String taskName;

    @Column(name = "taskDescription")
    private String taskDescription;

    @Column(name = "priorityId")
    private long priorityId;

    @ManyToOne
    @JoinColumn(name = "createdById",referencedColumnName = "userId")
    private User createdById;

    @ManyToOne
    @JoinColumn(name = "stageId",referencedColumnName = "stageId")
    private Stage stageId;

    @ManyToOne
    @JoinColumn(name = "reportsToId",referencedColumnName = "userId")
    private User reportsToId;

    @ManyToOne
    @JoinColumn(name = "relatedTaskId",referencedColumnName = "taskId")
    private Task relatedTaskId;

    @Column(name = "deletionStateCode")
    private int deletionStateCode = 0;

    @Column(name = "createdOn") // LocalDateTime
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name = "taskDeadlineDate")
    private LocalDateTime taskDeadlineDate;

    @JsonIgnore
    @OneToMany(mappedBy = "relatedTaskId")
    private List<Task> relatedTasks;

    @JsonIgnore
    @OneToMany(mappedBy = "linkedTaskId")
    private List<Comment> comments;


    public List<GeneralCommentResponse> toGeneralCommentResponses(){
        List<GeneralCommentResponse> generalComments = new ArrayList<>();

        for(var eleman : comments){
            if(eleman.getDeletionStateCode() == 1) continue;
            GeneralCommentResponse currentComment = new GeneralCommentResponse();
            currentComment.setCommentId(eleman.getCommentId());
            currentComment.setContent(eleman.getContent());
            currentComment.setLikeCount(eleman.getLikes().size());
            currentComment.setWrittenById(eleman.getWrittenById().toGeneralUserAuthenticationResponse());
            currentComment.setCreatedOn(eleman.getCreatedOn());

            generalComments.add(currentComment);
        }

        return generalComments;
    }

    public TaskResponse toTaskResponse(){
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(taskId);
        taskResponse.setDescription(taskDescription);
        taskResponse.setName(taskName);
        return taskResponse;
    }


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

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(long priorityId) {
        this.priorityId = priorityId;
    }


    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
    }


    public User getResposibleId() {
        return resposibleId;
    }

    public void setResposibleId(User resposibleId) {
        this.resposibleId = resposibleId;
    }

    public Task getRelatedTaskId() {
        return relatedTaskId;
    }

    public void setRelatedTaskId(Task relatedTaskId) {
        this.relatedTaskId = relatedTaskId;
    }

    public List<Task> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(List<Task> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }

    public User getCreatedById() {
        return createdById;
    }

    public void setCreatedById(User createdById) {
        this.createdById = createdById;
    }

    public Stage getStageId() {
        return stageId;
    }

    public void setStageId(Stage stageId) {
        this.stageId = stageId;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public User getReportsToId() {
        return reportsToId;
    }


    public void setReportsToId(User reportsToId) {
        this.reportsToId = reportsToId;
    }

   
}

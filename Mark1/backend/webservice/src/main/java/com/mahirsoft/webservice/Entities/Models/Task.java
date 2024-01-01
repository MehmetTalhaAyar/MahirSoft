package com.mahirsoft.webservice.Entities.Models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    long taskId;
    
    @ManyToOne
    @JoinColumn(name = "responsibleId",referencedColumnName = "userId")
    UserAuthentication resposibleId;

    @ManyToOne
    @JoinColumn(name = "projectId",referencedColumnName = "projectId")
    Project projectId;

    @Column(name = "taskName")
    String taskName;

    @Column(name = "taskDescription")
    String taskDescription;

    @Column(name = "priorityId")
    long priorityId;

    @ManyToOne
    @JoinColumn(name = "createdById",referencedColumnName = "userId")
    UserAuthentication createdById;

    @Column(name = "commentId")
    long commentId;

    @ManyToOne
    @JoinColumn(name = "stageId",referencedColumnName = "stageId")
    Stage stageId;

    @ManyToOne
    @JoinColumn(name = "relatedTaskId",referencedColumnName = "taskId")
    Task relatedTaskId;

    @Column(name = "deletionStateCode")
    int deletionStateCode = 0;

    @Column(name = "createdOn")
    Date createdOn = Date.valueOf(LocalDate.now());

    @Column(name = "taskDeadlineDate")
    Date taskDeadlineDate;

    @JsonIgnore
    @OneToMany(mappedBy = "relatedTaskId")
    List<Task> relatedTasks;


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

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
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

    public UserAuthentication getUserAuthenticationresposibleId() {
        return resposibleId;
    }

    public void setUserAuthenticationresposibleId(UserAuthentication userAuthenticationresposibleId) {
        this.resposibleId = userAuthenticationresposibleId;
    }

    public UserAuthentication getResposibleId() {
        return resposibleId;
    }

    public void setResposibleId(UserAuthentication resposibleId) {
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

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public UserAuthentication getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UserAuthentication createdById) {
        this.createdById = createdById;
    }

    public Stage getStageId() {
        return stageId;
    }

    public void setStageId(Stage stageId) {
        this.stageId = stageId;
    }

   
}

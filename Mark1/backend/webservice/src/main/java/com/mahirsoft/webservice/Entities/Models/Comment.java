package com.mahirsoft.webservice.Entities.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "commentId")
    private long commentId;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "writtenById",referencedColumnName = "userId")
    private UserAuthentication writtenById;

    @ManyToOne
    @JoinColumn(name = "linkedTaskId", referencedColumnName = "taskId")
    private Task linkedTaskId;

    @Column(name = "likeCount")
    private int likeCount = 0;

    @Column(name = "deletionStateCode")
    private int deletionStateCode = 0;

    @Column(name = "createdOn")
    private LocalDateTime createdOn = LocalDateTime.now();

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
    }


    public UserAuthentication getWrittenById() {
        return writtenById;
    }

    public void setWrittenById(UserAuthentication writtenById) {
        this.writtenById = writtenById;
    }

    public Task getLinkedTaskId() {
        return linkedTaskId;
    }

    public void setLinkedTaskId(Task linkedTaskId) {
        this.linkedTaskId = linkedTaskId;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }



    
}

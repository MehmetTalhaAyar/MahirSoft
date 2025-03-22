package com.mahirsoft.webservice.Entities.Models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.CommentResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralCommentResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private User writtenById;

    @ManyToOne
    @JoinColumn(name = "linkedTaskId", referencedColumnName = "taskId")
    private Task linkedTaskId;

    @Column(name = "deletionStateCode")
    private int deletionStateCode = 0;

    @Column(name = "createdOn")
    private LocalDateTime createdOn = LocalDateTime.now();

    @JsonIgnore
    @OneToMany(mappedBy = "commentId")
    private List<CommentLike> likes;



    public GeneralCommentResponse toGeneralCommentResponse(){
        GeneralCommentResponse generalCommentResponse = new GeneralCommentResponse();
        generalCommentResponse.setCommentId(commentId);
        generalCommentResponse.setContent(content);
        generalCommentResponse.setWrittenById(writtenById.toGeneralUserAuthenticationResponse());
        generalCommentResponse.setCreatedOn(createdOn);
        return generalCommentResponse;
    }

    public CommentResponse toCommentResponse(){
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentId(commentId);
        commentResponse.setContent(content);
        return commentResponse;
    }

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

    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
    }


    public User getWrittenById() {
        return writtenById;
    }

    public void setWrittenById(User writtenById) {
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

    public List<CommentLike> getLikes() {
        return likes;
    }

    public void setLikes(List<CommentLike> likes) {
        this.likes = likes;
    }



    
}

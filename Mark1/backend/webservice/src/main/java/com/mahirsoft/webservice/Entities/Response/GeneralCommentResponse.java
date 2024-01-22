package com.mahirsoft.webservice.Entities.Response;

import java.time.LocalDateTime;

public class GeneralCommentResponse {

    private long commentId;

    private String content;

    private GeneralUserAuthenticationResponse writtenById;

    private int likeCount;
    
    private LocalDateTime createdOn;

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

    public GeneralUserAuthenticationResponse getWrittenById() {
        return writtenById;
    }

    public void setWrittenById(GeneralUserAuthenticationResponse writtenById) {
        this.writtenById = writtenById;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
    
}

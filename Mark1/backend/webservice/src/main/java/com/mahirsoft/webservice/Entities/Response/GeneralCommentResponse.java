package com.mahirsoft.webservice.Entities.Response;


public class GeneralCommentResponse {

    private long commentId;

    private String content;

    private GeneralUserAuthenticationResponse writtenById;

    private int likeCount;

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
    
}

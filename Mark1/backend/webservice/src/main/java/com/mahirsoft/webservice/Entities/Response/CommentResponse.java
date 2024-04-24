package com.mahirsoft.webservice.Entities.Response;

public class CommentResponse {
    
    
    private long commentId;

    private String content;

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
}

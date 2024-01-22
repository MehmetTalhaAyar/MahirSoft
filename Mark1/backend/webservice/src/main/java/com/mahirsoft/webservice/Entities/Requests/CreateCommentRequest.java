package com.mahirsoft.webservice.Entities.Requests;

public class CreateCommentRequest {

    private String content;

    private long linkedTaskId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLinkedTaskId() {
        return linkedTaskId;
    }

    public void setLinkedTaskId(long linkedTaskId) {
        this.linkedTaskId = linkedTaskId;
    }
    
}

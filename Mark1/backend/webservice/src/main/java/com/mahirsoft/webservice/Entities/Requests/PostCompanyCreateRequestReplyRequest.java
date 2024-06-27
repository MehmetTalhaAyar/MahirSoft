package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotNull;

public class PostCompanyCreateRequestReplyRequest {

    @NotNull
    private long requestId;

    @NotNull
    private Boolean isOkey;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Boolean getIsOkey() {
        return isOkey;
    }

    public void setIsOkey(Boolean isOkey) {
        this.isOkey = isOkey;
    }

   
    
}

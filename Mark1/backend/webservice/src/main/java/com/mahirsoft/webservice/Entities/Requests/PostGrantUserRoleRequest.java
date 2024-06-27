package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotNull;

public class PostGrantUserRoleRequest {

    @NotNull
    private long userRoleId;

    @NotNull
    private long userId;

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    
}

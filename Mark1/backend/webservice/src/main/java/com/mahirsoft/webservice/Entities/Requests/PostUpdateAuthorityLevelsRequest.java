package com.mahirsoft.webservice.Entities.Requests;

import java.util.List;

public class PostUpdateAuthorityLevelsRequest {

    private long userRoleId;
    
    private List<Integer> authorityNumbers;

    public List<Integer> getAuthorityNumber() {
        return authorityNumbers;
    }

    public void setAuthorityNumber(List<Integer> authorityNumbers) {
        this.authorityNumbers = authorityNumbers;
    }

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public List<Integer> getAuthorityNumbers() {
        return authorityNumbers;
    }

    public void setAuthorityNumbers(List<Integer> authorityNumbers) {
        this.authorityNumbers = authorityNumbers;
    }


}

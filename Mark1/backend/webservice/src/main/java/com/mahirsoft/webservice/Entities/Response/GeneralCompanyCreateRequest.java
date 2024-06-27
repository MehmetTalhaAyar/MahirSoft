package com.mahirsoft.webservice.Entities.Response;

public class GeneralCompanyCreateRequest {

    private long companyCreateRequestId;

    private String name;

    private String description;

    private GeneralUserAuthenticationResponse user;

    public long getCompanyCreateRequestId() {
        return companyCreateRequestId;
    }

    public void setCompanyCreateRequestId(long companyCreateRequestId) {
        this.companyCreateRequestId = companyCreateRequestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeneralUserAuthenticationResponse getUser() {
        return user;
    }

    public void setUser(GeneralUserAuthenticationResponse user) {
        this.user = user;
    }
    
}

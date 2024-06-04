package com.mahirsoft.webservice.Entities.Requests;

import com.mahirsoft.webservice.Entities.Response.GeneralCompanyResponse;

import jakarta.validation.constraints.NotNull;

public class PostReplyToCompanyInvitationRequest {

    @NotNull
    private GeneralCompanyResponse companyInfo;

    @NotNull
    private Boolean reply;

    public GeneralCompanyResponse getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(GeneralCompanyResponse companyInfo) {
        this.companyInfo = companyInfo;
    }

    public Boolean getReply() {
        return reply;
    }

    public void setReply(Boolean reply) {
        this.reply = reply;
    }

   
}

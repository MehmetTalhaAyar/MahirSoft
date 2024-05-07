package com.mahirsoft.webservice.Entities.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CompanyCreateRequests")
public class CompanyCreateRequest {
    
    
    @Id
    @GeneratedValue
    @Column(name = "companyInvitationId")
    private long companyInvitationId;

    @Column(name = "status")
    private int status; // 1 accepted // 2 rejected // 3 pending 

    @ManyToOne
    @JoinColumn(name = "companyId",referencedColumnName = "companyId")
    private Company companyId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAuthentication userId;

    public long getCompanyInvitationId() {
        return companyInvitationId;
    }

    public void setCompanyInvitationId(long companyInvitationId) {
        this.companyInvitationId = companyInvitationId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public UserAuthentication getUserId() {
        return userId;
    }

    public void setUserId(UserAuthentication userId) {
        this.userId = userId;
    }

    
}

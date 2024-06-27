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
public class CompanyCreateRequest { // şirket oluşturmak için bir request 
    
    @Id
    @GeneratedValue
    @Column(name = "companyCreateRequestId")
    private long companyCreateRequestId;

    @Column(name = "status")
    private int status; // 1 accepted // 2 rejected // 3 pending 

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAuthentication userId;

    public static class CompanyCreateRequestCodes {

        public static int ACCEPTED = 1;
        public static int REJECTED = 2;
        public static int PENDING = 3;    
        
    }

    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   

    public UserAuthentication getUserId() {
        return userId;
    }

    public void setUserId(UserAuthentication userId) {
        this.userId = userId;
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

    public long getCompanyCreateRequestId() {
        return companyCreateRequestId;
    }

    public void setCompanyCreateRequestId(long companyCreateRequestId) {
        this.companyCreateRequestId = companyCreateRequestId;
    }

    
}

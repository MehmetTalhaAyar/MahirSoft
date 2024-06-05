package com.mahirsoft.webservice.Entities.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CompanyInvitations")
public class CompanyInvitation { // şirkete davet için bir entity

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
    @JoinColumn(name = "userWhoReceiveInvitation")
    private UserAuthentication userWhoReceiveInvitation;

    @ManyToOne
    @JoinColumn(name = "userWhoSendInvitation")
    private UserAuthentication userWhoSendInvitation;


    public static class CompanyInvitationCodes {

        public static int ACCEPTED = 1;
        public static int REJECTED = 2;
        public static int PENDING = 3;    
        
    }


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




    public UserAuthentication getUserWhoReceiveInvitation() {
        return userWhoReceiveInvitation;
    }




    public void setUserWhoReceiveInvitation(UserAuthentication userWhoReceiveInvitation) {
        this.userWhoReceiveInvitation = userWhoReceiveInvitation;
    }




    public UserAuthentication getUserWhoSendInvitation() {
        return userWhoSendInvitation;
    }




    public void setUserWhoSendInvitation(UserAuthentication userWhoSendInvitation) {
        this.userWhoSendInvitation = userWhoSendInvitation;
    }




    
}

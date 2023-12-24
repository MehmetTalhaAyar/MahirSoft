package com.mahirsoft.webservice.Entities.Models;



import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;



@Entity
@Table(name="UserAuthentication",uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class UserAuthentication {
    
    @Id
    @GeneratedValue
    @Column(name ="userId")
    private long userId;

    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "gsm")
    private String gsm;

    @Column(name = "title")
    private String title;

    @Column(name = "reportsToId")
    long reportsToId;

    @Column(name = "companyId")
    long companyId;

    @Column(name = "authorityTypeId")
    long authorityTypeId;

    @Column(name = "createdOn")
    Date createdOn;

    @Column(name = "deletionStateCode")
    int deletionStateCode;

    @Column(name = "createdById")
    long createdById;

    @JsonIgnore
    @OneToMany(mappedBy = "resposibleId")
    List<Task> responsibleTasks;

    @JsonIgnore
    @OneToMany(mappedBy = "resposibleId")
    List<Task> tasksCretedBy;



    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getReportsToId() {
        return reportsToId;
    }

    public void setReportsToId(long reportsToId) {
        this.reportsToId = reportsToId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getAuthorityTypeId() {
        return authorityTypeId;
    }

    public void setAuthorityTypeId(long authorityTypeId) {
        this.authorityTypeId = authorityTypeId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public List<Task> getResponsibleTasks() {
        return responsibleTasks;
    }

    public void setResponsibleTasks(List<Task> responsibleTasks) {
        this.responsibleTasks = responsibleTasks;
    }

    
 


}

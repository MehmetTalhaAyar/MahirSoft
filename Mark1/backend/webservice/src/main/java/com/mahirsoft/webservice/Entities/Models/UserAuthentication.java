package com.mahirsoft.webservice.Entities.Models;



import java.sql.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "reportsToId",referencedColumnName = "userId")
    private UserAuthentication reportsToId;

    @ManyToOne
    @JoinColumn(name = "companyId",referencedColumnName = "companyId")
    private Company companyId;

    @Column(name = "authorityTypeId")
    private long authorityTypeId;

    @Column(name = "createdOn")
    private Date createdOn;

    @Column(name = "deletionStateCode")
    private int deletionStateCode = 0;

    @ManyToOne
    @JoinColumn(name = "createdById",referencedColumnName = "userId")
    private UserAuthentication createdById;

    @JsonIgnore
    @OneToMany(mappedBy = "resposibleId")
    private List<Task> responsibleTasks;

    @JsonIgnore
    @OneToMany(mappedBy = "createdById")
    private List<Task> tasksCreatedBy;

    @JsonIgnore
    @OneToMany(mappedBy =  "leadingPersonId")
    private List<Project> managedProjects;

    @JsonIgnore
    @OneToMany(mappedBy = "createdById")
    private List<Stage> createdStages;

    @JsonIgnore
    @OneToMany(mappedBy = "reportsToId")
    private List<UserAuthentication> managedUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "createdById")
    private List<UserAuthentication> createdUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "managerId")
    private List<Company> companyLead;

    @JsonIgnore
    @OneToMany(mappedBy = "writtenById")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private List<ProjectUser> projects;


    public GeneralUserAuthenticationResponse toGeneralUserAuthenticationResponse() {
        GeneralUserAuthenticationResponse generalUser = new GeneralUserAuthenticationResponse();

        generalUser.setEmail(email);
        generalUser.setName(name);
        generalUser.setFullName(name + " " + surname);
        generalUser.setGsm(gsm);
        generalUser.setUserId(userId);
        generalUser.setSurname(surname);
        
        return generalUser;
    }





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

    public List<Task> getResponsibleTasks() {
        return responsibleTasks;
    }

    public void setResponsibleTasks(List<Task> responsibleTasks) {
        this.responsibleTasks = responsibleTasks;
    }

    public UserAuthentication getReportsToId() {
        return reportsToId;
    }

    public void setReportsToId(UserAuthentication reportsToId) {
        this.reportsToId = reportsToId;
    }

    

    public List<Project> getManagedProjects() {
        return managedProjects;
    }

    public void setManagedProjects(List<Project> managedProjects) {
        this.managedProjects = managedProjects;
    }

    public List<Stage> getCreatedStages() {
        return createdStages;
    }

    public void setCreatedStages(List<Stage> createdStages) {
        this.createdStages = createdStages;
    }

    public List<UserAuthentication> getCreatedUsers() {
        return createdUsers;
    }

    public void setCreatedUsers(List<UserAuthentication> createdUsers) {
        this.createdUsers = createdUsers;
    }

    public List<UserAuthentication> getManagedUsers() {
        return managedUsers;
    }

    public void setManagedUsers(List<UserAuthentication> managedUsers) {
        this.managedUsers = managedUsers;
    }

    public List<Task> getTasksCreatedBy() {
        return tasksCreatedBy;
    }

    public void setTasksCreatedBy(List<Task> tasksCreatedBy) {
        this.tasksCreatedBy = tasksCreatedBy;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public UserAuthentication getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UserAuthentication createdById) {
        this.createdById = createdById;
    }

    public List<Company> getCompanyLead() {
        return companyLead;
    }

    public void setCompanyLead(List<Company> companyLead) {
        this.companyLead = companyLead;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<ProjectUser> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectUser> projects) {
        this.projects = projects;
    }

    
 


}

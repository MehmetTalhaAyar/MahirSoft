package com.mahirsoft.webservice.Entities.Models;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralUserResponse;
import com.mahirsoft.webservice.Entities.Response.TaskCountResponse;
import com.mahirsoft.webservice.Entities.Response.UserAuthenticationResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;



@Entity
@Table(name="User",uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
    
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

    @Lob
    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "userRoleId",referencedColumnName = "userRoleId")
    private UserRole userRoleId;

    @ManyToOne
    @JoinColumn(name = "reportsToId",referencedColumnName = "userId")
    private User reportsToId;

    @ManyToOne
    @JoinColumn(name = "companyId",referencedColumnName = "companyId")
    private Company companyId;

    @Column(name = "createdOn")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name = "deletionStateCode")
    private int deletionStateCode = 0;

    @ManyToOne
    @JoinColumn(name = "createdById",referencedColumnName = "userId")
    private User createdById;

    @JsonIgnore
    @OneToMany(mappedBy = "resposibleId")
    private List<Task> responsibleTasks;

    @JsonIgnore
    @OneToMany(mappedBy = "createdById")
    private List<Task> tasksCreatedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "reportsToId")
    private List<Task> tasksReporter;

    @JsonIgnore
    @OneToMany(mappedBy =  "leadingPersonId")
    private List<Project> managedProjects;

    @JsonIgnore
    @OneToMany(mappedBy = "createdById")
    private List<Stage> createdStages;

    @JsonIgnore
    @OneToMany(mappedBy = "reportsToId")
    private List<User> managedUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "createdById")
    private List<User> createdUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "managerId")
    private List<Company> companyLead;

    @JsonIgnore
    @OneToMany(mappedBy = "writtenById")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private List<ProjectUser> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private List<CommentLike> likes;

    @JsonIgnore
    @OneToMany(mappedBy = "userWhoReceiveInvitation")
    private List<CompanyInvitation> recievedCompanyInvitations;

    @JsonIgnore
    @OneToMany(mappedBy = "userWhoSendInvitation")
    private List<CompanyInvitation> sentCompanyInvitations;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private List<CompanyCreateRequest> companyCreateRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private List<Notification> notifications;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    



    public GeneralUserResponse toGeneralUserAuthenticationResponse() {
        GeneralUserResponse generalUser = new GeneralUserResponse();

        generalUser.setEmail(email);
        generalUser.setName(name);
        generalUser.setFullName(name + " " + surname);
        generalUser.setGsm(gsm);
        generalUser.setUserId(userId);
        generalUser.setSurname(surname);
        generalUser.setTitle(title);
        generalUser.setImage(image);
        if(companyId != null)
            generalUser.setCompany(companyId.toCompanyResponse());
        generalUser.setRoleName(userRoleId.getName());
        
        return generalUser;
    }

    public UserAuthenticationResponse toUserAuthenticationResponse(){
        UserAuthenticationResponse userAuthenticationResponse = new UserAuthenticationResponse();
        userAuthenticationResponse.setFullName(name + " " + surname);
        userAuthenticationResponse.setId(userId);

        return userAuthenticationResponse;

    }

    public TaskCountResponse toTaskCountResponse(){
        TaskCountResponse taskCountResponse = new TaskCountResponse();
        int dueToday = 0;
        int pendingTasks = 0; 
        int totalTask = 0;


        if(responsibleTasks != null)
        {

            for(var eleman :responsibleTasks){
                if(eleman.getStageId().getProjectId().getDeletionStateCode() == 1) continue;

                totalTask = totalTask + 1;

                if(eleman.getTaskDeadlineDate() != null){
                    if(localdatetimeCompare(eleman.getTaskDeadlineDate())){
                        dueToday = dueToday + 1;
                    }

                }                

                if(eleman.getStageId().getName().equals("Pending")){
                    pendingTasks = pendingTasks + 1;

                }
                

            }
            taskCountResponse.setDueTaskCount(dueToday);
            taskCountResponse.setPendingTaskCount(pendingTasks);
            taskCountResponse.setTotalTaskCount(totalTask);
            
        }
        
        return taskCountResponse;

    }

    private boolean localdatetimeCompare(LocalDateTime taskdate){
        LocalDateTime today = LocalDateTime.now();
        return taskdate.getYear() == today.getYear() && taskdate.getMonth() == today.getMonth() && taskdate.getDayOfYear() == today.getDayOfYear();
    }

    public List<GeneralProjectResponse> toGeneralProjectResponses(){
        List<GeneralProjectResponse> projectList = new ArrayList<>();

        for(var eleman: projects){

            if(eleman.getProjectId().getDeletionStateCode() == 1) continue;

            GeneralProjectResponse currentProject = new GeneralProjectResponse();

            currentProject.setId(eleman.getProjectId().getProjectId());
            currentProject.setCreatedOn(eleman.getProjectId().getCreatedOn());
            currentProject.setLeadingPerson(eleman.getProjectId().getLeadingPersonId().toGeneralUserAuthenticationResponse());
            currentProject.setStages(eleman.getProjectId().toGeneralStageResponses());
            currentProject.setName(eleman.getProjectId().getName());
            currentProject.setMembers(eleman.getProjectId().toGeneralUserAuthenticationResponses());

            projectList.add(currentProject);

        }

        return projectList;

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

    public User getReportsToId() {
        return reportsToId;
    }

    public void setReportsToId(User reportsToId) {
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

    public List<User> getCreatedUsers() {
        return createdUsers;
    }

    public void setCreatedUsers(List<User> createdUsers) {
        this.createdUsers = createdUsers;
    }

    public List<User> getManagedUsers() {
        return managedUsers;
    }

    public void setManagedUsers(List<User> managedUsers) {
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

    public User getCreatedById() {
        return createdById;
    }

    public void setCreatedById(User createdById) {
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





    public LocalDateTime getCreatedOn() {
        return createdOn;
    }





    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Task> getTasksReporter() {
        return tasksReporter;
    }

    public void setTasksReporter(List<Task> tasksReporter) {
        this.tasksReporter = tasksReporter;
    }

    public UserRole getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UserRole userRoleId) {
        this.userRoleId = userRoleId;
    }

    public List<CommentLike> getLikes() {
        return likes;
    }

    public void setLikes(List<CommentLike> likes) {
        this.likes = likes;
    }

    
 


}

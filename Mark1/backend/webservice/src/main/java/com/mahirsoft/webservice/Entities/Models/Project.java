package com.mahirsoft.webservice.Entities.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralStageResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.StageResponse;
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


@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "projectId")
    private long projectId;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description = "New Project";

    @ManyToOne
    @JoinColumn(name = "leadingPersonId", referencedColumnName = "userId")
    private UserAuthentication leadingPersonId;

    @Column(name = "deletionStateCode")
    private int deletionStateCode = 0;

    @Column(name = "createdOn")
    private LocalDateTime createdOn = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "companyId",referencedColumnName = "companyId")
    private Company companyId;

    @JsonIgnore
    @OneToMany(mappedBy = "projectId")
    private List<Stage> stages;

    @JsonIgnore
    @OneToMany(mappedBy = "projectId")
    private List<ProjectUser> projectMembers;

    public Project() {
    }

    public GeneralProjectResponse toGeneralProjectResponse(){
        GeneralUserAuthenticationResponse leadPerson = toLeadPerson();

        GeneralProjectResponse response = new GeneralProjectResponse();
        response.setId(projectId);
        response.setCreatedOn(createdOn);
        response.setName(name);
        response.setDescription(description);
        response.setLeadingPerson(leadPerson);
        response.setStages(toGeneralStageResponses());
        response.setMembers(toGeneralUserAuthenticationResponses());

        return response;

    }

    public List<GeneralUserAuthenticationResponse> toGeneralUserAuthenticationResponses(){
        List<GeneralUserAuthenticationResponse> members = new ArrayList<>();

        for(var eleman : projectMembers){
            GeneralUserAuthenticationResponse user = new GeneralUserAuthenticationResponse();
            var currentUser = eleman.getUserId();
            
            if(currentUser.getCompanyId() != null)
                user.setCompany(currentUser.getCompanyId().toCompanyResponse());

            user.setEmail(currentUser.getEmail());
            user.setFullName(currentUser.getName() + " " +currentUser.getSurname());
            user.setGsm(currentUser.getGsm());
            user.setName(currentUser.getName());
            user.setSurname(currentUser.getSurname());
            user.setUserId(currentUser.getUserId());
            user.setTitle(currentUser.getTitle());
            user.setImage(currentUser.getImage());
            user.setRoleName(currentUser.getUserRoleId().getName());

            members.add(user);

        }

        return members;
    }

    public List<GeneralStageResponse> toGeneralStageResponses(){
        List<GeneralStageResponse> stageResponses = new ArrayList<>();
        for(var eleman : stages){
            GeneralStageResponse generalStageResponse = new GeneralStageResponse();
            generalStageResponse.setId(eleman.getStageId());
            generalStageResponse.setName(eleman.getName());
            generalStageResponse.setTasks(eleman.toTaskResponses());
            generalStageResponse.setSequence(eleman.getSequence());

            stageResponses.add(generalStageResponse);
        }

        return stageResponses;
    }


    public List<StageResponse> toStageResponses(){
        List<StageResponse> stageResponses = new ArrayList<>();
        for(var eleman : stages){
            StageResponse stageResponse = new StageResponse();
            stageResponse.setId(eleman.getStageId());
            stageResponse.setName(eleman.getName());

            stageResponses.add(stageResponse);
        }

        return stageResponses;
    }

    public List<UserAuthenticationResponse> toUserAuthenticationResponses(){
        List<UserAuthenticationResponse> userAuthenticationResponses = new ArrayList<>();
        for(var eleman : projectMembers){
            userAuthenticationResponses.add(eleman.getUserId().toUserAuthenticationResponse());
        }

        return userAuthenticationResponses;

    }

    public GeneralUserAuthenticationResponse toLeadPerson(){
        GeneralUserAuthenticationResponse leadPerson = new GeneralUserAuthenticationResponse();
        leadPerson.setEmail(leadingPersonId.getEmail());
        leadPerson.setGsm(leadingPersonId.getGsm());
        leadPerson.setName(leadingPersonId.getName());
        leadPerson.setSurname(leadingPersonId.getSurname());
        leadPerson.setUserId(leadingPersonId.getUserId());
        leadPerson.setImage(leadingPersonId.getImage());
        leadPerson.setTitle(leadingPersonId.getTitle());

        if(leadingPersonId.getCompanyId() != null) //companysi olmayan kullanıcı için
            leadPerson.setCompany(leadingPersonId.getCompanyId().toCompanyResponse());

        leadPerson.setFullName(leadingPersonId.getName()+ " " + leadingPersonId.getSurname());


        return leadPerson;

    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAuthentication getLeadingPersonId() {
        return leadingPersonId;
    }

    public void setLeadingPersonId(UserAuthentication leadingPersonId) {
        this.leadingPersonId = leadingPersonId;
    }

    public int getDeletionStateCode() {
        return deletionStateCode;
    }

    public void setDeletionStateCode(int deletionStateCode) {
        this.deletionStateCode = deletionStateCode;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public List<ProjectUser> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(List<ProjectUser> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

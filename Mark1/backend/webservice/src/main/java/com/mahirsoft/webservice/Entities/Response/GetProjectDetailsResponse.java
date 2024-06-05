package com.mahirsoft.webservice.Entities.Response;

import java.util.List;

public class GetProjectDetailsResponse {

    private String name;

    private String description;

    private GeneralUserAuthenticationResponse projectLead;

    private List<GeneralUserAuthenticationResponse> projectMembers;

    private List<GeneralStageResponse> projectStages;

    private GetProjectTaskCount taskCounts;

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

    public GeneralUserAuthenticationResponse getProjectLead() {
        return projectLead;
    }

    public void setProjectLead(GeneralUserAuthenticationResponse projectLead) {
        this.projectLead = projectLead;
    }

    public List<GeneralUserAuthenticationResponse> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(List<GeneralUserAuthenticationResponse> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public List<GeneralStageResponse> getProjectStages() {
        return projectStages;
    }

    public void setProjectStages(List<GeneralStageResponse> projectStages) {
        this.projectStages = projectStages;
    }

    public GetProjectTaskCount getTaskCounts() {
        return taskCounts;
    }

    public void setTaskCounts(GetProjectTaskCount taskCounts) {
        this.taskCounts = taskCounts;
    }
    
}

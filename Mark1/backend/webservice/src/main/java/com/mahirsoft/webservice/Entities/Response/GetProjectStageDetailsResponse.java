package com.mahirsoft.webservice.Entities.Response;

import java.util.List;

public class GetProjectStageDetailsResponse {
    
    private List<GeneralStageResponse> stages;


    public List<GeneralStageResponse> getStages() {
        return stages;
    }

    public void setStages(List<GeneralStageResponse> stages) {
        this.stages = stages;
    }


}

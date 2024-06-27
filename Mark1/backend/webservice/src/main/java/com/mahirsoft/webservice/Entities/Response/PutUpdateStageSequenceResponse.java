package com.mahirsoft.webservice.Entities.Response;

public class PutUpdateStageSequenceResponse {

    GeneralStageResponse newUp;

    GeneralStageResponse newDown;

    public GeneralStageResponse getNewUp() {
        return newUp;
    }

    public void setNewUp(GeneralStageResponse newUp) {
        this.newUp = newUp;
    }

    public GeneralStageResponse getNewDown() {
        return newDown;
    }

    public void setNewDown(GeneralStageResponse newDown) {
        this.newDown = newDown;
    }
    
}

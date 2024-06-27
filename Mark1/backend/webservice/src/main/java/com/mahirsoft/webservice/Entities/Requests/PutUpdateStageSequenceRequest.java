package com.mahirsoft.webservice.Entities.Requests;

import jakarta.validation.constraints.NotNull;

public class PutUpdateStageSequenceRequest {

    @NotNull
    private long stageGoingUp;

    @NotNull
    private long stageGoingDown;

    public long getStageGoingUp() {
        return stageGoingUp;
    }

    public void setStageGoingUp(long stageGoingUp) {
        this.stageGoingUp = stageGoingUp;
    }

    public long getStageGoingDown() {
        return stageGoingDown;
    }

    public void setStageGoingDown(long stageGoingDown) {
        this.stageGoingDown = stageGoingDown;
    }
    
}

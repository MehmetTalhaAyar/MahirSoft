package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.StageService;
import com.mahirsoft.webservice.Entities.Response.GeneralStageResponse;

@RestController
@RequestMapping("api/v1/stage")
public class StageController {

    StageService service;

    public StageController(StageService service) {
        this.service = service;
    }

    

    @GetMapping("/{id}")
    public GeneralStageResponse getStage(@PathVariable long id){

        var stage = service.getStage(id);

        if(stage == null) return null;

    
        return stage.toGeneralStageResponse();
    }




}
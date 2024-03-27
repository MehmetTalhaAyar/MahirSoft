package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.StageService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.Response.GeneralStageResponse;
import com.mahirsoft.webservice.security.DefaultUser;

@RestController
@RequestMapping("api/v1/stage")
public class StageController {

    StageService service;

    PermissionService permissionService;


    public StageController(StageService service, PermissionService permissionService) {
        this.service = service;
        this.permissionService = permissionService;
    }


    @GetMapping("/{id}")
    public GeneralStageResponse getStage(@PathVariable long id,@AuthenticationPrincipal DefaultUser currentUser){

        permissionService.isTherePermission(currentUser, AuthorizationCodes.SUPER_ADMIN);

        var stage = service.getStage(id);

        if(stage == null) return null;

    
        return stage.toGeneralStageResponse();
    }




}

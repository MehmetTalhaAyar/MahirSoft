package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.PermissionService;
import com.mahirsoft.webservice.Business.concretes.StageService;
import com.mahirsoft.webservice.Business.concretes.PermissionService.AuthorizationCodes;
import com.mahirsoft.webservice.Entities.ResponseMessage;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Requests.PutUpdateStageNameRequest;
import com.mahirsoft.webservice.Entities.Requests.PutUpdateStageSequenceRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralStageResponse;
import com.mahirsoft.webservice.Entities.Response.PutUpdateStageSequenceResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/stage")
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

    @PostMapping("/create/{projectId}")
    public ResponseEntity<?> createStage(@PathVariable long projectId ,@Valid @RequestBody CreateStageRequest createStageRequest, @AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isInThisProject(currentUser, projectId,AuthorizationCodes.STAGE_CREATE);

        var stage = service.createStage(createStageRequest,user,projectId);

        if(stage == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        GeneralStageResponse generalStageResponse = stage.toGeneralStageResponse();

        return new ResponseEntity<GeneralStageResponse>(generalStageResponse,HttpStatus.CREATED);
    }

    @PutMapping("/update") 
    public ResponseEntity<?> updateStage(@Valid @RequestBody PutUpdateStageNameRequest putUpdateStageNameRequest, @AuthenticationPrincipal DefaultUser currentUser ){

        permissionService.isInThisProjectFindByStageId(currentUser, putUpdateStageNameRequest.getStageId(),AuthorizationCodes.STAGE_UPDATE);

        var stage = service.updateStage(putUpdateStageNameRequest);

        if(stage == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        GeneralStageResponse generalStageResponse = stage.toGeneralStageResponse();

        return new ResponseEntity<GeneralStageResponse>(generalStageResponse,HttpStatus.OK);
    }

    @PutMapping("/update/sequence")
    public ResponseEntity<?> handleUpdateStageSequence(@Valid @RequestBody PutUpdateStageSequenceRequest putUpdateStageSequenceRequest,@AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.STAGE_UPDATE);

        var response = service.updateStageSequence(putUpdateStageSequenceRequest);

        if(response == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        return new ResponseEntity<PutUpdateStageSequenceResponse>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{stageId}") // şuan gerçekten siliyor sonra düzelt bunu
    public ResponseEntity<?> handleDeleteRequest(@PathVariable long stageId, @AuthenticationPrincipal DefaultUser currentUser){

        var user = permissionService.isTherePermission(currentUser, AuthorizationCodes.STAGE_DELETE);

        var stage = service.softDeleteStage(stageId);

        if(stage == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Stage deleted."),HttpStatus.OK);
    }





}

package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.ProjectAndStageService;
import com.mahirsoft.webservice.Entities.Requests.CreateStageRequest;
import com.mahirsoft.webservice.Entities.Response.PostProjectAndStageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projectandstage")
public class ProjectAndStageController {

    ProjectAndStageService projectAndStageService;
    

    public ProjectAndStageController(ProjectAndStageService projectAndStageService) {
        this.projectAndStageService = projectAndStageService;
    }



    @PostMapping("/{id}")
    public ResponseEntity<?> addStage(@PathVariable long id,@Valid @RequestBody CreateStageRequest createStageRequest){
        String body = "Project Not Found";

        var response = projectAndStageService.addStageToProject(id, createStageRequest);
        if(response == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(400));
        }

        PostProjectAndStageResponse postProjectAndStageResponse = new PostProjectAndStageResponse();
        postProjectAndStageResponse.setName(response.getName());
        postProjectAndStageResponse.setProjectId(response.getProjectId());

        return new ResponseEntity<PostProjectAndStageResponse>(postProjectAndStageResponse, HttpStatusCode.valueOf(200));



    }
    
}

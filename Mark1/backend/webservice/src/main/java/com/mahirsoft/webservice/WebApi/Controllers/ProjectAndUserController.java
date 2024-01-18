package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.ProjectAndUserService;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;
import com.mahirsoft.webservice.Entities.Response.PostProjectAndUserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/createproject")
public class ProjectAndUserController {

    ProjectAndUserService projectAndUserService;

    public ProjectAndUserController(ProjectAndUserService projectAndUserService) {
        this.projectAndUserService = projectAndUserService;
    }
    // burası sadece yazılım tarafında kullanılıyor.
    @PostMapping("/{id}")
    public ResponseEntity<?> createProjectWithLead(@PathVariable long id,@Valid @RequestBody CreateProjectRequest createProjectRequest){
        
        var response = projectAndUserService.createProjectWithLeading(id, createProjectRequest);

        if(response == null){
            return null;
        }

        GeneralUserAuthenticationResponse leadPerson = response.toLeadPerson();
        
        PostProjectAndUserResponse postProjectAndUserResponse = new PostProjectAndUserResponse();
        postProjectAndUserResponse.setCreatedOn(response.getCreatedOn());
        postProjectAndUserResponse.setName(response.getName());
        postProjectAndUserResponse.setStages(response.getStages());
        postProjectAndUserResponse.setLeadingPerson(leadPerson);

        return new ResponseEntity<PostProjectAndUserResponse>(postProjectAndUserResponse,HttpStatusCode.valueOf(200));


    }
}

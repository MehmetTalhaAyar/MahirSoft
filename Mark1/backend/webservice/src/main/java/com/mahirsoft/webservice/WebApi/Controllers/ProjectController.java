package com.mahirsoft.webservice.WebApi.Controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.ProjectService;
import com.mahirsoft.webservice.Entities.Requests.CreateProjectRequest;
import com.mahirsoft.webservice.Entities.Response.GetProjectResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {

    ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable long id){
        String body = "Project Not Found";
        var project = projectService.getProject(id);

        if(project == null){
            return new ResponseEntity<String>(body, HttpStatusCode.valueOf(400));

        }
        
        GetProjectResponse getProjectResponse = new GetProjectResponse();
        getProjectResponse.setCreatedOn(project.getCreatedOn());
        getProjectResponse.setLeadingPerson(project.getLeadingPersonId());
        getProjectResponse.setName(project.getName());
        getProjectResponse.setStages(project.getStages());


        return new ResponseEntity<GetProjectResponse>(getProjectResponse, HttpStatusCode.valueOf(200));
        

    }

    @PostMapping("/add")
    public ResponseEntity<?> addProject(@Valid @RequestBody CreateProjectRequest createProjectRequest){
        String body = "Project created";

        projectService.createProject(createProjectRequest);

        return new ResponseEntity<String>(body,HttpStatusCode.valueOf(201));
    }
    
}

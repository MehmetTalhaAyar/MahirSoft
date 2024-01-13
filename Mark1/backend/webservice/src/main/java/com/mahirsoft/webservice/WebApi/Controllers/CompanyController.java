package com.mahirsoft.webservice.WebApi.Controllers;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahirsoft.webservice.Business.concretes.CompanyService;
import com.mahirsoft.webservice.Business.concretes.UserAuthenticationService;
import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Requests.CreateCompnayRequest;
import com.mahirsoft.webservice.Entities.Requests.PostAddUserToCompanyRequest;
import com.mahirsoft.webservice.Entities.Response.GeneralCompanyResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralProjectResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralUserAuthenticationResponse;
import com.mahirsoft.webservice.security.DefaultUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {

    CompanyService companyService;

    UserAuthenticationService userAuthenticationService;

    public CompanyController(CompanyService companyService,UserAuthenticationService userAuthenticationService){
        this.companyService = companyService;
        this.userAuthenticationService = userAuthenticationService;

    }

    @PostMapping("/create")
    public ResponseEntity<?>  createCompany(@Valid @RequestBody CreateCompnayRequest createCompnayRequest,@AuthenticationPrincipal DefaultUser authentication){

        var user = userAuthenticationService.findById(authentication.getId());

        if(user == null || user.getCompanyId() != null) return new ResponseEntity<String>("Something went wrong!", HttpStatusCode.valueOf(404));

        Company newCompany = new Company();

        newCompany.setName(createCompnayRequest.getName());
        newCompany.setManagerId(user);
        newCompany.setDescription(createCompnayRequest.getDescription());

        var company = companyService.createCompany(newCompany);
        if(company == null) return new ResponseEntity<String>("Something went wrong!", HttpStatusCode.valueOf(404));
        user.setCompanyId(newCompany);
        var updateduser = userAuthenticationService.updateUser(user);
        if(updateduser == null) return new ResponseEntity<String>("Something went wrong!", HttpStatusCode.valueOf(404));

        GeneralCompanyResponse companyResponse = new GeneralCompanyResponse();
        companyResponse.setDescription(company.getDescription());
        companyResponse.setId(company.getCompanyId());
        companyResponse.setName(company.getName());
        
        GeneralUserAuthenticationResponse generaluser = updateduser.toGeneralUserAuthenticationResponse();
        generaluser.setCompany(company.toCompanyResponse());
        
        companyResponse.setManager(generaluser);


        return new ResponseEntity<GeneralCompanyResponse>(companyResponse, HttpStatusCode.valueOf(201));
    }

    @PostMapping("/addemployee/{id}")
    public ResponseEntity<?> addEmployeeToCompany(@PathVariable long id,@RequestBody PostAddUserToCompanyRequest postAddUserToCompanyRequest){

        var user = userAuthenticationService.findByEmail(postAddUserToCompanyRequest.getEmail());
        if(user == null) return new ResponseEntity<String>("Something went wrong!",  HttpStatusCode.valueOf(404));

        var company = companyService.getCompany(id);

        if(company == null) return new ResponseEntity<String>("Something went wrong!",  HttpStatusCode.valueOf(404));

        for(var eleman : company.getCompanyMembers()){
            if (eleman.getUserId() == user.getUserId()){
                
                return new ResponseEntity<String>("User already in",  HttpStatusCode.valueOf(400));
            } 
        }

        

        user.setCompanyId(company);
        userAuthenticationService.updateUser(user);

        return new ResponseEntity<String>("User added",  HttpStatusCode.valueOf(200));
    }

    @GetMapping("/members")
    public List<GeneralUserAuthenticationResponse> getcompanyMembers(@AuthenticationPrincipal DefaultUser user){
        var chosenUser = userAuthenticationService.findById(user.getId());

        var company = companyService.getCompany(chosenUser.getCompanyId().getCompanyId());

        return company.toListGeneralUserAuthenticationResponses();

    }


    @GetMapping("/projects")
    public List<GeneralProjectResponse> getCompanyProjects(@AuthenticationPrincipal DefaultUser user){
        var currentUser = userAuthenticationService.findById(user.getId());

        if(currentUser == null) return null;

        return currentUser.getCompanyId().toGeneralProjectResponses();
    }


    
}

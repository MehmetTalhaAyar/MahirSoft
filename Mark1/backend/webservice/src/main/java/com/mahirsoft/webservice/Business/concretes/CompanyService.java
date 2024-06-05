package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.CompanyInvitationRepository;
import com.mahirsoft.webservice.DataAccess.CompanyRepository;
import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Exceptions.ResourceNotFoundException;
import com.mahirsoft.webservice.Entities.Exceptions.UserAlreadyInTheCompanyException;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;
import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.CompanyInvitation;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Models.CompanyInvitation.CompanyInvitationCodes;
import com.mahirsoft.webservice.Entities.Requests.PostAddUserToCompanyRequest;
import com.mahirsoft.webservice.Entities.Requests.PostReplyToCompanyInvitationRequest;
import com.mahirsoft.webservice.Entities.Response.CompanyResponse;
import com.mahirsoft.webservice.Entities.Response.GeneralCompanyResponse;

import jakarta.validation.Valid;

@Service
public class CompanyService {

    CompanyRepository companyRepository;

    CompanyInvitationRepository companyInvitationRepository;

    UserAuthenticationRepository userAuthenticationRepository;


    public CompanyService(CompanyRepository companyRepository, CompanyInvitationRepository companyInvitationRepository,
            UserAuthenticationRepository userAuthenticationRepository) {
        this.companyRepository = companyRepository;
        this.companyInvitationRepository = companyInvitationRepository;
        this.userAuthenticationRepository = userAuthenticationRepository;
    }

    public Company getCompany(long id){
        return companyRepository.findById(id).orElse(null); 
    }

    public Company createCompany(Company company){
        return companyRepository.save(company);
    }
    

    public List<UserAuthentication> getCompanyMembers(long id){
        var company = companyRepository.findById(id).orElseThrow();

        return company.getCompanyMembers();
    }


    public CompanyInvitation addANewMember(long companyId, PostAddUserToCompanyRequest postAddUserToCompanyRequest,
            UserAuthentication userWhoSentInvitation) {

        var userWhoReceiveInvitation = userAuthenticationRepository.findByEmail(postAddUserToCompanyRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException());    

        var company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException());

        if(userWhoReceiveInvitation.getCompanyId().getCompanyId() == company.getCompanyId()){
            throw new UserAlreadyInTheCompanyException();
        }

        CompanyInvitation companyInvitation = new CompanyInvitation();
        companyInvitation.setCompanyId(company);
        companyInvitation.setStatus(CompanyInvitationCodes.PENDING);
        companyInvitation.setUserWhoReceiveInvitation(userWhoReceiveInvitation);
        companyInvitation.setUserWhoSendInvitation(userWhoSentInvitation);

        return companyInvitationRepository.save(companyInvitation);
    }

    public List<CompanyInvitation> checkCompanyInvitations(UserAuthentication user ,int status) {
        return companyInvitationRepository.findByUserWhoReceiveInvitationAndStatus(user,status);
    }

    public Company replyCompanyInvitation(PostReplyToCompanyInvitationRequest postReplyToCompanyInvitationRequest,
            UserAuthentication user) {
        
        
        var company = companyRepository.findById(postReplyToCompanyInvitationRequest.getCompanyInfo().getId()).orElseThrow(()-> new ResourceNotFoundException());

        var companyInvitation = companyInvitationRepository.findByCompanyIdAndUserWhoReceiveInvitation(company, user).orElseThrow(()-> new ResourceNotFoundException());
                
        if(postReplyToCompanyInvitationRequest.getReply()){

            companyInvitation.setStatus(CompanyInvitationCodes.ACCEPTED);
        }
        else{
            companyInvitation.setStatus(CompanyInvitationCodes.REJECTED);
        } 

        companyInvitation = companyInvitationRepository.save(companyInvitation);

        return companyInvitation.getCompanyId();
    }
}

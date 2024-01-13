package com.mahirsoft.webservice.Business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.DataAccess.CompanyRepository;
import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

@Service
public class CompanyService {
    CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
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
}

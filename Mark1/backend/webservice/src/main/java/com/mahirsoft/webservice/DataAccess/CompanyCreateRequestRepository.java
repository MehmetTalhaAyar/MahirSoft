package com.mahirsoft.webservice.DataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.CompanyCreateRequest;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public interface CompanyCreateRequestRepository extends JpaRepository<CompanyCreateRequest,Long> {
    


    CompanyCreateRequest findByStatusAndUserId(int status,UserAuthentication user);

    List<CompanyCreateRequest> findByStatus(int staus); 
}

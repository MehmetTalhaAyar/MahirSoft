package com.mahirsoft.webservice.DataAccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.CompanyInvitation;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public interface CompanyInvitationRepository extends JpaRepository<CompanyInvitation,Long> {

    List<CompanyInvitation> findByUserWhoReceiveInvitationAndStatus(UserAuthentication userWhoReceiveInvitation,int status);

    Optional<CompanyInvitation> findByCompanyIdAndUserWhoReceiveInvitation(Company company,UserAuthentication userAuthentication);
    
}

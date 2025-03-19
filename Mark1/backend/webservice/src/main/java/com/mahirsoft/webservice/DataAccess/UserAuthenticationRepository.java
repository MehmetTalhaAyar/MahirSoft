package com.mahirsoft.webservice.DataAccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public interface UserAuthenticationRepository  extends JpaRepository<UserAuthentication,Long>{

    Optional<UserAuthentication> findById(long id);

    Optional<UserAuthentication> findByEmail(String email);

    List<UserAuthentication> findFirst5ByCompanyIdAndNameContaining(Company company,String searchKey);

    List<UserAuthentication> findFirst5BycompanyId(Company company);

    @Query("select u from UserAuthentication u where u.companyId = :company and u not in (:excludeUsers) and u.email LIKE %:searchKey%")
    List<UserAuthentication> findFirst3ByCompanyIdAndNameContaining(Company company,String searchKey,List<UserAuthentication> excludeUsers);
    
    @Query("select u from UserAuthentication u where u.companyId = :company and u not in (:excludeUsers)")
    List<UserAuthentication> findFirst3ByCompanyId(Company company,List<UserAuthentication> excludeUsers);
    
}

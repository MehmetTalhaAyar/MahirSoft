package com.mahirsoft.webservice.DataAccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;

public interface UserAuthenticationRepository  extends JpaRepository<UserAuthentication,Long>{

    UserAuthentication findById(long id);

    Optional<UserAuthentication> findByEmail(String email);

    List<UserAuthentication> findFirst5ByCompanyIdAndNameContaining(Company company,String searchKey);

    List<UserAuthentication> findFirst5BycompanyId(Company company);
    
    
}

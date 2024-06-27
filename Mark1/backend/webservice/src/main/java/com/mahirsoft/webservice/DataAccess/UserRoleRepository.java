package com.mahirsoft.webservice.DataAccess;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    
    UserRole findById(long id);

    UserRole findByUserRoleIdAndCompanyIdIsNull(long id);

    List<UserRole> findFirst3ByCompanyIdAndName(Company company,String searchKey);

    List<UserRole> findFirst3ByCompanyId(Company companyId);

}

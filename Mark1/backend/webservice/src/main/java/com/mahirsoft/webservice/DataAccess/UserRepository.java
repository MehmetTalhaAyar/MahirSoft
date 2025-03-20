package com.mahirsoft.webservice.DataAccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mahirsoft.webservice.Entities.Models.Company;
import com.mahirsoft.webservice.Entities.Models.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    List<User> findFirst5ByCompanyIdAndNameContaining(Company company,String searchKey);

    List<User> findFirst5BycompanyId(Company company);

    @Query("select u from User u where u.companyId = :company and u not in (:excludeUsers) and u.email LIKE %:searchKey%")
    List<User> findFirst3ByCompanyIdAndNameContaining(Company company,String searchKey,List<User> excludeUsers);
    
    @Query("select u from User u where u.companyId = :company and u not in (:excludeUsers)")
    List<User> findFirst3ByCompanyId(Company company,List<User> excludeUsers);
    
}

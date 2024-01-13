package com.mahirsoft.webservice.DataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahirsoft.webservice.Entities.Models.Company;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    
}

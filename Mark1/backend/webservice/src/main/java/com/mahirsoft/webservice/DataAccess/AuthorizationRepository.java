package com.mahirsoft.webservice.DataAccess;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


import com.mahirsoft.webservice.Entities.Models.Authorization;

public interface AuthorizationRepository extends JpaRepository<Authorization,Long> {

    List<Authorization> findByAuthorizationIdNot(long authorizationId);
    
}

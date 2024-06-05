package com.mahirsoft.webservice.Entities.Validations;

import com.mahirsoft.webservice.DataAccess.UserAuthenticationRepository;
import com.mahirsoft.webservice.Entities.Models.UserAuthentication;
import com.mahirsoft.webservice.Entities.Validations.Annotations.UniqueEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    UserAuthenticationRepository userAuthenticationRepository;

    UniqueEmailValidator(UserAuthenticationRepository userAuthenticationRepository){
        this.userAuthenticationRepository = userAuthenticationRepository;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        UserAuthentication inDB = userAuthenticationRepository.findByEmail(value).orElse(null);
        
        return inDB == null;
    }
    
}

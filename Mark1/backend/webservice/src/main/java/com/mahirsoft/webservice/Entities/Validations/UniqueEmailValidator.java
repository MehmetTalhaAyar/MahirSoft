package com.mahirsoft.webservice.Entities.Validations;

import com.mahirsoft.webservice.DataAccess.UserRepository;
import com.mahirsoft.webservice.Entities.Models.User;
import com.mahirsoft.webservice.Entities.Validations.Annotations.UniqueEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    UserRepository userAuthenticationRepository;

    UniqueEmailValidator(UserRepository userAuthenticationRepository){
        this.userAuthenticationRepository = userAuthenticationRepository;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        User inDB = userAuthenticationRepository.findByEmail(value).orElse(null);
        
        return inDB == null;
    }
    
}

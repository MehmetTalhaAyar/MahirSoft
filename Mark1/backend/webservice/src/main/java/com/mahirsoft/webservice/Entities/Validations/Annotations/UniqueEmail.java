package com.mahirsoft.webservice.Entities.Validations.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mahirsoft.webservice.Entities.Validations.UniqueEmailValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = {UniqueEmailValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "E-mail in use!";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

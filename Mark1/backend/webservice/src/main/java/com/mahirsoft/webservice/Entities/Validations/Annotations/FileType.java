package com.mahirsoft.webservice.Entities.Validations.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mahirsoft.webservice.Entities.Validations.FileTypeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = {FileTypeValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileType {
    
    String message() default "Only {types} files are allowed";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};

    String[] types();
    
}

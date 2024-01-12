package com.mahirsoft.webservice.WebApi.ExceptionHandlers;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mahirsoft.webservice.Entities.Errors.ApiError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleMethodArgutmentNotValidException(MethodArgumentNotValidException exception,HttpServletRequest request){
        ApiError apiError = new ApiError();
        //path değiştirilecek
        apiError.setPath(request.getRequestURI());
        apiError.setMessage("Validation error");
        apiError.setStatus(400);
        var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage,(existing,replacing) -> existing));

        apiError.setValidationErrors(validationErrors);
        return new ResponseEntity<ApiError>(apiError, HttpStatusCode.valueOf(apiError.getStatus()));
    }

    
}

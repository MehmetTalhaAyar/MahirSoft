package com.mahirsoft.webservice.WebApi.ExceptionHandlers;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mahirsoft.webservice.Entities.Errors.ApiError;
import com.mahirsoft.webservice.Entities.Exceptions.PermissionDeniedException;
import com.mahirsoft.webservice.Entities.Exceptions.UserNotFoundException;

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


    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception,HttpServletRequest request){
        ApiError apiError = new ApiError();

        apiError.setPath(request.getRequestURI());
        apiError.setMessage("User Not Found");
        apiError.setStatus(400);

        return new ResponseEntity<>(apiError,HttpStatusCode.valueOf(apiError.getStatus()));
    }

    @ExceptionHandler(PermissionDeniedException.class)
    ResponseEntity<?> handlePermissionDeniedException(PermissionDeniedException exception,HttpServletRequest request){
        ApiError apiError = new ApiError();

        apiError.setPath(request.getRequestURI());
        apiError.setMessage("Permission Denied");
        apiError.setStatus(401);

        return new ResponseEntity<>(apiError,HttpStatusCode.valueOf(apiError.getStatus()));
    }

    
}

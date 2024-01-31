package org.mindswap.springtheknife.aspects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ValidationErrorsHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Map<String,String> handleException(MethodArgumentNotValidException ex){

        Map<String, String> errors = new
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return errors;

    }
}

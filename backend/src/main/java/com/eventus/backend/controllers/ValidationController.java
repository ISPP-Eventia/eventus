package com.eventus.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class ValidationController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> res = new HashMap<>();
        StringBuilder errorMessage= new StringBuilder();
        for(ObjectError error: ex.getBindingResult().getAllErrors()){
            errorMessage.append(((FieldError) error).getField()).append(": ").append(error.getDefaultMessage()).append(" ");
        }
        res.put("error", errorMessage.toString());
        return res;
    }
}

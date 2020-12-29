package com.example.exception.handler;

import com.example.exception.model.ExceptionRepresenter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionRepresenter> defaultExceptionHandler(Exception exception){
        return new ResponseEntity<>(new ExceptionRepresenter(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

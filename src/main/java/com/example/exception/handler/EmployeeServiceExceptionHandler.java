package com.example.exception.handler;

import com.example.exception.EmployeeNotFoundException;
import com.example.exception.model.ExceptionRepresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeServiceExceptionHandler {

    Logger logger = LoggerFactory.getLogger(EmployeeServiceExceptionHandler.class);

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ExceptionRepresenter> handleEmployeeNotFoundException(EmployeeNotFoundException exception){
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity(new ExceptionRepresenter(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}

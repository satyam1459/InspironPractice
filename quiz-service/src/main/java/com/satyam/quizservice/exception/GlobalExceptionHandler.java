package com.satyam.quizservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.NoSuchObjectException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseEntity<String> noSuchObjectExceptionHandler(){
        return new ResponseEntity<>("No Element with this ID. Please input valid Question ID..!", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementExceptionHandler(){
        return new ResponseEntity<>("No info with this ID. Please input valid ID..!", HttpStatus.BAD_REQUEST);
    }
}

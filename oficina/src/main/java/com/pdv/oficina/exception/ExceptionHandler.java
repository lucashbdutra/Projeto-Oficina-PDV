package com.pdv.oficina.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    public ResponseEntity<Object> handleAnyException(Exception e, WebRequest request){
        ErrorMessage error = new ErrorMessage(new Date(), e.getMessage(),request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

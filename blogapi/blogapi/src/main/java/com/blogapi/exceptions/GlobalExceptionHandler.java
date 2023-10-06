package com.blogapi.exceptions;

import com.blogapi.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler { //responseentityexceptionhandler class is custom handler if any exception occurs it comes to this classs
    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class) //this is gonna handle what exception we have here resourcenitfoundexception....specifice exception
    public ResponseEntity<ErrorDetails>
    handleResourceNotFoundException(ResourceNotFoundException exception,//exception handler method ...and this actually acts like catch block
                                    WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),//when exception occur we require date exception message and description ...
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}

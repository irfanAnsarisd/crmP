package com.irfanDemo.exception;

import com.irfanDemo.payload.ErrorDetails;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

// catch block - its act like global class to handle the exception
@ControllerAdvice
public class HandleException {

//    @ExceptionHandler(ResourceNotFound.class)
//    public ResponseEntity<String> handleEmployeeNotFoundException(ResourceNotFound e){
//        return new ResponseEntity<>("Record not found", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(ResourceNotFound.class)
//    public ResponseEntity<ErrorDetails> handleEmployeeNotFoundException(ResourceNotFound e){
//        ErrorDetails errorDetails = new ErrorDetails(
//                new Date(),
//                e.getMessage()
//        );
//        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> handleEmployeeNotFoundException(
            ResourceNotFound e,
            WebRequest request
    ){
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleEmployeeNotFoundException(
            Exception e,
            WebRequest request
    ){
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

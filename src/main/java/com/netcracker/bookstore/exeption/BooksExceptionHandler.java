package com.netcracker.bookstore.exeption;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.bookstore.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class BooksExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UpdateExeption.class)
    public ResponseEntity<ErrorResponse> handleUpdateExeption(UpdateExeption ex){
        List<String> details=new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("Такого id несуществует",details), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AmountExeption.class)
    public ResponseEntity<ErrorResponse> handleAmountExeption(AmountExeption ex){
        List<String> details=new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("не хватает книг на складе",details), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorResponse> hanleJsonProcessingException(JsonProcessingException ex){
        List<String> details=new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("не удалось преобразовать в json",details), HttpStatus.CONFLICT);
    }

}

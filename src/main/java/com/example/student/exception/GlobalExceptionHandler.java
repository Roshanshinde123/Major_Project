package com.example.student.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.ReflectionUtils.getField;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>>handleNotFound(ResourceNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String,Object>>handleDuplicate(DuplicateResourceException ex){
        return buildResponse(HttpStatus.CONFLICT,ex.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>>handleValidation(MethodArgumentNotValidException ex){
        Map<String,Object> body=new HashMap<>();
        Map<String,String>errors=new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));


        body.put("status",400);
        body.put("error","Bad Request");
        body.put("message",errors);
        body.put("timestampt", LocalDateTime.now());

        return ResponseEntity.badRequest().body(body);

    }

    private ResponseEntity<Map<String ,Object>>buildResponse(HttpStatus status ,String message){
        Map<String ,Object>body=new HashMap<>();
        body.put("timeStamp",LocalDateTime.now());
        body.put("status",status.value());
        body.put("error",status.getReasonPhrase());
        body.put("message",message);


        return ResponseEntity.status(status).body(body);

    }

}

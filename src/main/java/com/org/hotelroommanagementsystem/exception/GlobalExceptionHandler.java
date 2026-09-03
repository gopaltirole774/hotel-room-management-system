package com.org.hotelroommanagementsystem.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRoomNotFound(RoomNotFoundException e, HttpServletRequest request) {

        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(LocalDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> response.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }


}

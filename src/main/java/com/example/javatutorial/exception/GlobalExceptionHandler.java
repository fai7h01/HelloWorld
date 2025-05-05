package com.example.javatutorial.exception;

import com.example.javatutorial.dto.ExceptionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Throwable.class, Exception.class, RuntimeException.class})
    public ResponseEntity<ExceptionWrapper> handleGenericExceptions(Throwable exception) {
        log.error(exception.getMessage());
        return status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionWrapper.builder()
                        .success(false)
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ExceptionWrapper> handleNotAuthorizedException(NotAuthorizedException exception) {
        log.error(exception.getMessage());
        return status(HttpStatus.FORBIDDEN)
                .body(ExceptionWrapper.builder()
                        .success(false)
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}

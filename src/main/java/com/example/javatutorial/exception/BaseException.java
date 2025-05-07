package com.example.javatutorial.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final HttpStatus status;
    private final String errorDetails;

    public BaseException(HttpStatus status) {
        super("An error occurred");
        this.status = status;
        this.errorDetails = String.format("Error occurred at %S: ", System.currentTimeMillis());
    }

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.errorDetails = String.format("Error occurred at %S: %S", System.currentTimeMillis(), message);
    }

    public BaseException(String message, HttpStatus status, String errorDetails) {
        super(message);
        this.status = status;
        this.errorDetails = errorDetails;
    }
}

package com.project.officequiz.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidUserDetailsException extends RuntimeException {
    private HttpStatus httpStatus;
    public InvalidUserDetailsException(HttpStatus status,String message) {
        super(message);
        this.httpStatus = status;
    }
}
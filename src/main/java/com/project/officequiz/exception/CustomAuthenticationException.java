package com.project.officequiz.exception;

public class CustomAuthenticationException extends RuntimeException{

    public CustomAuthenticationException(String message) {
        super(message);
    }
}

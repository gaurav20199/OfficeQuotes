package com.project.officequiz.exception;

public class InvalidActivationCodeException extends RuntimeException{

    public InvalidActivationCodeException(String message) {
        super(message);
    }
}

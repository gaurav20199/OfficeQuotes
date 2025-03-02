package com.project.officequiz.exception;

import com.project.officequiz.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserDetailsException.class)
    public ModelAndView handleInvalidUserDetailsException(InvalidUserDetailsException ex, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("status", ex.getHttpStatus());
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
/*
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
 */

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleCustomAuthenticationException(CustomAuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "User is unauthorized", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidActivationCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidActivationCodeException(InvalidActivationCodeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "User is Unauthorized","Activation Token is not valid");
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGlobalException(Exception ex, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("error", HttpStatus.INTERNAL_SERVER_ERROR.name().replaceAll("_"," "));
        modelAndView.addObject("message", HttpStatus.INTERNAL_SERVER_ERROR.name().replaceAll("_"," "));
        return modelAndView;
    }
}

package com.project.officequiz.security.handler;

import com.project.officequiz.exception.CustomAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws CustomAuthenticationException, IOException {
        String errorMessage = "Invalid username or password.";

        if (exception instanceof DisabledException) {
            errorMessage = "Your account is not activated. Please activate it first.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Incorrect username or password. Please try again.";
        }

        HttpSession session = request.getSession();
        session.setAttribute("errorMessage",errorMessage);
        response.sendRedirect("/login?error=true");
    }
}

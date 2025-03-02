package com.project.officequiz.security.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

//@Component
public class AccountStatusFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public AccountStatusFilter(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getParameter("username");

        if (username != null && !username.isBlank()) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                String presentedPassword = request.getParameter("password");
                System.out.println(!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword()));
                if (!userDetails.isEnabled()) {
                    request.getSession().setAttribute("errorMessage", "Your account is not activated. Please activate it first.");
                    response.sendRedirect("/login?error=true");
                    return;
                }
            } catch (BadCredentialsException ignored) {
                request.getSession().setAttribute("errorMessage", "Invalid Credentials");
                response.sendRedirect("/login?error=true");
            }catch (UsernameNotFoundException e) {
                request.getSession().setAttribute("errorMessage", e.getMessage());
                response.sendRedirect("/login?error=true");
            }catch (Exception e) {

            }
        }

        filterChain.doFilter(request, response);
    }
}

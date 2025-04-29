package com.project.officequiz.security.config;

import com.project.officequiz.security.handler.AccountStatusFilter;
import com.project.officequiz.security.handler.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity httpSecurity, CustomAuthenticationFailureHandler failureHandler) throws Exception {
        return httpSecurity.
                formLogin(
                        customizer -> customizer.loginPage("/login").
                        defaultSuccessUrl("/quiz",true)
                        //failureHandler(failureHandler)
                ).
                authorizeHttpRequests(
                        auth ->
                        auth.requestMatchers(
                                        "/login","/register", "/activate/**",
                                        "/", "/css/**", "/images/**","/js/**","/favicon.ico","/registration-success","/token"
                                ).
                        permitAll().anyRequest().authenticated()
                ).
                logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            request.getSession().setAttribute("logoutSuccess", true);
                            response.sendRedirect("/login");
                        })
                ).

        //addFilterAt(accountStatusFilter, UsernamePasswordAuthenticationFilter.class).
                build();
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

package com.project.officequiz.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.
                formLogin(customizer -> customizer.loginPage("/login").
                        defaultSuccessUrl("/quiz",true)).
                authorizeHttpRequests(auth ->
                        auth.requestMatchers("/login","/quiz","/register", "/css/**", "/images/**").
                        permitAll().anyRequest().authenticated()).
                build();
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

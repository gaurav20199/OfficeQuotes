package com.project.officequiz.service;

import java.util.Date;
import java.util.UUID;

import com.project.officequiz.dao.ActivationTokenRepository;
import com.project.officequiz.entity.ActivationToken;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ActivationTokenService {

    @Autowired
    private ActivationTokenRepository activationTokenRepository;

    public ActivationToken createToken(String email) {
        ActivationToken token = new ActivationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setEmail(email);
        token.setExpiryDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // 24 hours

        return activationTokenRepository.save(token);
    }
}

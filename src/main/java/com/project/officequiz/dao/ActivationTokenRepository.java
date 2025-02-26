package com.project.officequiz.dao;

import com.project.officequiz.entity.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {
    
    Optional<ActivationToken> findByToken(String token);
    
    Optional<ActivationToken> findByEmail(String email);
    
    void deleteByToken(String token);
}

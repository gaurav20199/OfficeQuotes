package com.project.officequiz.dao;

import com.project.officequiz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserManagementRepository extends JpaRepository<User,Integer> {

    Optional<User> findUserByUuid(String uuid);
    Optional<User> findUserByUserName(String userName);
}

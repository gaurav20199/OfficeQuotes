package com.project.officequiz.dao;

import com.project.officequiz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserManagementRepository extends JpaRepository<User,Integer> {

    Optional<User> findUserByUuid(String uuid);
    Optional<User> findUserByUserName(String userName);

    @Query("select u from User u where u.userName=?1 or u.email=?2")
    List<User> isValidUser(String userName, String email);
}

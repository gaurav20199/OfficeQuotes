package com.project.officequiz.repository;

import com.project.officequiz.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserManagementRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findUserByUuid(String uuid);
    Optional<Users> findUserByUserName(String userName);

    @Query("select u from Users u where u.userName=?1 or u.email=?2")
    List<Users> isValidUser(String userName, String email);

    Optional<Users> findByUserNameAndIsActiveTrue(String userName);
}

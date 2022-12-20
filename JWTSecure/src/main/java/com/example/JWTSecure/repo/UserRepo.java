package com.example.JWTSecure.repo;

import com.example.JWTSecure.domain.Role;
import com.example.JWTSecure.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByUsernameAndPassword(String username, String password);
    User findByUsername(String user);
    User findByEmail(String email);
    User findByPhone(String phone);
    User findTopByOrderByIdDesc();
    User findUserByUsername(String username);
    @Modifying
    @Query("UPDATE User user SET user.active = false WHERE user.id = ?1")
    int deactive(Long id);
    @Modifying
    @Query("UPDATE User user SET user.active = false WHERE user.id = ?1")
    int deactiveAca(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE User a SET a.enabled=true, a.active=true WHERE a.email=?1")
    int enableAppUser(String email);
    @Query("UPDATE User a SET a.active=true WHERE a.email=?1")
    int activeAppUser(String email);
}


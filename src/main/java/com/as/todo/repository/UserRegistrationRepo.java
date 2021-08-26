package com.as.todo.repository;

import com.as.todo.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegistrationRepo extends JpaRepository<UserRegistration, Long> {

    Optional<UserRegistration> findByEmail(String email);
    boolean existsByEmail(String email);
}

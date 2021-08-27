package com.as.todo.service;

import com.as.todo.entity.UserRegistration;
import com.as.todo.repository.UserRegistrationRepo;
import com.as.todo.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);
    @Autowired
    private UserRegistrationRepo userRegistrationRepo;

    public UserRegistration registerUser(UserRegistration registration) {
        log.info("registerUser {}", registration);
        return this.userRegistrationRepo.save(registration);
    }

    public boolean existByEmail(String email){
        log.info("existByEmail {}", email);
        return userRegistrationRepo.existsByEmail(email);
    }
}

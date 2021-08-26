package com.as.todo.service;

import com.as.todo.entity.UserRegistration;
import com.as.todo.repository.UserRegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    @Autowired
    private UserRegistrationRepo userRegistrationRepo;

    public UserRegistration registerUser(UserRegistration registration) {
        return this.userRegistrationRepo.save(registration);
    }

    public boolean existByEmail(String email){
        return userRegistrationRepo.existsByEmail(email);
    }
}

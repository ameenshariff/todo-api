package com.as.todo.controller;

import com.as.todo.entity.UserRegistration;
import com.as.todo.payload.request.LoginRequest;
import com.as.todo.payload.response.JwtResponse;
import com.as.todo.payload.response.MessageResponse;
import com.as.todo.security.jwt.JwtUtils;
import com.as.todo.security.services.UserDetailsImpl;
import com.as.todo.service.UserRegistrationService;
import com.as.todo.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth")
public class UserRegistrationController {
    private final String URL = "http://localhost:8080/";
    @Autowired
    private UserRegistrationService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping(value = "/signin",
            produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        if(!userService.existByEmail(loginRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Email does not exists. Please register " + URL + "api/auth/resister"));

        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername()));
    }

    @PostMapping(value = "/register",
            produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistration user) {

        if(userService.existByEmail(user.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userService.registerUser(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

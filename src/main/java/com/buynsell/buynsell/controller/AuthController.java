package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.AuthenticationTokenResponse;
import com.buynsell.buynsell.payload.LoginRequest;
import com.buynsell.buynsell.payload.SignUpRequest;
import com.buynsell.buynsell.service.UserService;
import com.buynsell.buynsell.util.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthValidator authValidator;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Validate Inputs
        ResponseEntity responseEntity = authValidator.validateSignin(loginRequest);
        if (responseEntity != null) {
            return responseEntity;
        }
        // Encrypt Password
        loginRequest.setPassword(AESEncryption.encrypt(loginRequest.getPassword(), AuthKeys.getSecretKey()));
        // Request User from Service
        Optional<User> user = userService.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail());
        // Check if User exist
        if (!user.isPresent())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        // Check if password matches
        if (user.get().getPassword().equals(loginRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationTokenResponse(AuthenticationTokenUtil.generateToken(loginRequest.getUsernameOrEmail(), AuthKeys.getTokenSecretKey())));
        // If password does not match
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        // Validate inputs
        ResponseEntity responseEntity = authValidator.validateSignup(signUpRequest);
        if (responseEntity != null)
            return responseEntity;

        // Validations
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken!");
        } else if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Address already in use!");
        }

        if (userService.save(signUpRequest) == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");

        return ResponseEntity.status(HttpStatus.OK).body("Successfully Registered");

    }
}

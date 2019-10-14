package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.AuthenticationTokenResponse;
import com.buynsell.buynsell.payload.LoginRequest;
import com.buynsell.buynsell.payload.SignUpRequest;
import com.buynsell.buynsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Value("${secretKey}")
    private String secretKey;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        loginRequest.setPassword(AESEncryption.encrypt(loginRequest.getPassword(), secretKey));
        Optional<User> user = userService.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        if (user.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationTokenResponse(AuthenticationTokenUtil.generateToken(loginRequest.getUsernameOrEmail(), secretKey)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        // Validations
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("User is already taken!");
        } else if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Email Address already in use!");
        } else if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password not equal!");
        } else if (!signUpRequest.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid info!");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setActive(true);
        // encrypt password before saving

        user.setPassword(AESEncryption.encrypt(user.getPassword(), secretKey));

        // call service
        User result = userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully Registered");
    }
}

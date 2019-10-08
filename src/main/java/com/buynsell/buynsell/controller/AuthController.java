package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.ApiResponse;
import com.buynsell.buynsell.payload.AuthenticationTokenResponse;
import com.buynsell.buynsell.payload.LoginRequest;
import com.buynsell.buynsell.payload.SignUpRequest;
import com.buynsell.buynsell.service.AuthService;
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
    AuthService authService;

    @Value("${secretKey}")
    private String secretKey;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        loginRequest.setPassword(AESEncryption.encrypt(loginRequest.getPassword(), secretKey));
        Optional<User> user = authService.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail());
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
        if (authService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse(false, "User is already taken!"));
        }
        if (authService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse(true, "Email Address already in use!"));
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setActive(true);
        // encrypt password before saving

        user.setPassword(AESEncryption.encrypt(user.getPassword(), secretKey));

        // call service
        User result = authService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "Successfully Registered"));
    }
}

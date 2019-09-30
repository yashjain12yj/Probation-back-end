package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.ApiResponse;
import com.buynsell.buynsell.payload.LoginRequest;
import com.buynsell.buynsell.payload.SignUpRequest;
import com.buynsell.buynsell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        Optional<User> user = userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail());

        if (!user.isPresent())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Invalid username or password");
        if (user.get().getPassword().equals(loginRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.OK).body("Valid Info");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Invalid username or password");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.NO_CONTENT);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.NO_CONTENT);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setActive(true);

        // encrypt password before saving
        user.setPassword(user.getPassword());


        // call service
        User result = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "User registered successfully"));
    }
}

package com.buynsell.buynsell.controller;

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

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AuthValidator authValidator;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        ResponseEntity responseEntity = authValidator.validateSignup(signUpRequest);
        if (responseEntity != null)
            return responseEntity;
        if (userService.existsByUsername(signUpRequest.getUsername()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken!");
        else if (userService.existsByEmail(signUpRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Address already in use!");
        try {
            userService.save(signUpRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully Registered");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        ResponseEntity responseEntity = authValidator.validateSignin(loginRequest);
        if (responseEntity != null)
            return responseEntity;
        String token = null;
        try {
            token = userService.checkAuth(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if (token == null || token.equals(""))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationTokenResponse(token));
    }
}

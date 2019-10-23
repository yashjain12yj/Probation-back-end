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
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private AuthKeys authKeys;

    @Autowired
    private AuthenticationTokenUtil authenticationTokenUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        ResponseEntity responseEntity = authValidator.validateSignin(loginRequest);
        if (responseEntity != null)
            return responseEntity;
        loginRequest.setPassword(AESEncryption.encrypt(loginRequest.getPassword(), authKeys.getSecretKey()));
        Optional<User> user = userService.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail());
        if (!user.isPresent())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        if (user.get().getPassword().equals(loginRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationTokenResponse(authenticationTokenUtil.generateToken(loginRequest.getUsernameOrEmail(), authKeys.getTokenSecretKey())));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        ResponseEntity responseEntity = authValidator.validateSignup(signUpRequest);
        if (responseEntity != null)
            return responseEntity;
        if (userService.existsByUsername(signUpRequest.getUsername()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken!");
        else if (userService.existsByEmail(signUpRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Address already in use!");
        if (userService.save(signUpRequest) == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        return ResponseEntity.status(HttpStatus.OK).body("Successfully Registered");
    }
}

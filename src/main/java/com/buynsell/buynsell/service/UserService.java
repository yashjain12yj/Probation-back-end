package com.buynsell.buynsell.service;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.LoginRequest;
import com.buynsell.buynsell.payload.SignUpRequest;
import com.buynsell.buynsell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthKeys authKeys;

    @Autowired
    private AuthenticationTokenUtil authenticationTokenUtil;

    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail);
    }

    public String checkAuth(LoginRequest loginRequest) {
        loginRequest.setPassword(AESEncryption.encrypt(loginRequest.getPassword(), authKeys.getSecretKey()));
        Optional<User> user = findByUsernameOrEmail(loginRequest.getUsernameOrEmail());
        if (!user.isPresent())
            return null;
        if (user.get().getPassword().equals(loginRequest.getPassword()))
            return authenticationTokenUtil.generateToken(user.get().getUsername(), authKeys.getTokenSecretKey());
        return null;
    }


    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(SignUpRequest signUpRequest) throws Exception {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername().toLowerCase());
        user.setEmail(signUpRequest.getEmail().toLowerCase());
        user.setPassword(AESEncryption.encrypt(signUpRequest.getPassword(), authKeys.getSecretKey()));
        user.setActive(true);
        userRepository.save(user);
    }

}

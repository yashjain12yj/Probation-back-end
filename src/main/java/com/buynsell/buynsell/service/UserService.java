package com.buynsell.buynsell.service;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.model.User;
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

    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(SignUpRequest signUpRequest) {
        signUpRequest.setPassword(AESEncryption.encrypt(signUpRequest.getPassword(), authKeys.getSecretKey()));
        signUpRequest.setConfirmPassword(signUpRequest.getPassword());
        return userRepository.save(signUpRequest);
    }

}

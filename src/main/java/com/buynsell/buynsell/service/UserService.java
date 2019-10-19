package com.buynsell.buynsell.service;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.SignUpRequest;
import com.buynsell.buynsell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Value("${secretKey}")
    private String secretKey;


    @Autowired
    UserRepository userRepository;

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
        signUpRequest.setPassword(AESEncryption.encrypt(signUpRequest.getPassword(),secretKey));
        return userRepository.save(signUpRequest);
    }

}

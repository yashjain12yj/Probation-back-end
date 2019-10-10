package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoadDataController {
    @Autowired
    UserRepository userRepository;

    @Value("${secretKey}")
    private String secretKey;

    @GetMapping("/")
    public String loadData() {

        // Create User

        User user = new User();
        user.setName("Yash Jain");
        user.setActive(true);
        user.setEmail("yashjain@gmail.com");
        user.setUsername("yashjain");
        user.setPassword(AESEncryption.encrypt("yashjain", secretKey));

        userRepository.save(user);

        return "Data Loaded -> " + user.toString();
    }
}

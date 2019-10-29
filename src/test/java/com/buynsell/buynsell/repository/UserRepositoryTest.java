package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByUsernameOrEmail() {
        Optional<User> user = userRepository.findByUsernameOrEmail("jainy");
        assertEquals("Checking find by username or email", "jainy", user.get().getUsername());
    }

    @Test
    public void existsByUsername() {
        boolean flag = userRepository.existsByUsername("jainy");
        assertEquals("Checking user exist by username", true, flag);
        flag = userRepository.existsByUsername("jainying");
        assertEquals("Checking user exist by username", false, flag);

    }

    @Test
    public void existsByEmail() {
        boolean flag = userRepository.existsByEmail("jainy@arezzosky.com");
        assertEquals("Checking user exist by email", true, flag);
        flag = userRepository.existsByEmail("jainying@arezzosky.com");
        assertEquals("Checking user exist by email", false, flag);
    }
}
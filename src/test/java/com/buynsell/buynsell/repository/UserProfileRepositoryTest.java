package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    User user;

    @Before
    public void setUp() throws Exception {
        prepareTestData();
    }

    private void prepareTestData() {
        user = userRepository.findByUsernameOrEmail("bhagwat").get();
    }

    @Test
    public void changePassword() {
        int result = userProfileRepository.changePassword(user, "EoE7C06BLZJY8lBnjVWXdQ==");
        assertEquals("Checking changePassword repository", 1, result);
    }

    @Test
    public void getDashboard() {
        List items = userProfileRepository.getDashboard("jainy");

        assertEquals("Checking expected dashboard ", 2, items.size());
    }
}
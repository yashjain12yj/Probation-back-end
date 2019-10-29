package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.repository.UserRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User expectedUser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        prepareData();
    }

    public void prepareData() {
        expectedUser = new User();
        expectedUser.setName("Yash Jain");
        expectedUser.setUsername("jainy");
        expectedUser.setEmail("jainy@arezzosky.com");
        expectedUser.setId(1l);
    }

    @Test
    public void findByUsernameOrEmail() {
        Mockito.when(userRepository.findByUsernameOrEmail(expectedUser.getUsername())).thenReturn(Optional.of(expectedUser));
        Optional<User> actual = userService.findByUsernameOrEmail(expectedUser.getUsername());
        assertEquals("Check if user Find user by username or email is same as test data",this.expectedUser.getUsername(), actual.get().getUsername());
    }
}
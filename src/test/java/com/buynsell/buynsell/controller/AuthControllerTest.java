package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.service.UserService;
import com.buynsell.buynsell.util.AuthValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthValidator authValidator;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void registerUser() {

    }

    @Test
    public void authenticateUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/signin")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}

package com.buynsell.buynsell.service;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.ChangePasswordDTO;
import com.buynsell.buynsell.model.UserInfo;
import com.buynsell.buynsell.repository.UserProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

public class UserProfileServiceTest {

    @InjectMocks
    private UserProfileService userProfileService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserService userService;

    private UserInfo userInfo;
    private User expectedUser;

    private AuthKeys authKeys;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        prepareTestData();
    }

    @Test
    public void changePassword() throws Exception {
        Mockito.when(userService.findByUsernameOrEmail(userInfo.getEmail())).thenReturn(Optional.of(expectedUser));
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("Yash@123", "Yash@321", "Yash@321");
        doNothing().when(userProfileRepository).changePassword(expectedUser, AESEncryption.encrypt(changePasswordDTO.getNewPassword(), authKeys.getSecretKey()));
        int actual = userProfileService.changePassword(changePasswordDTO);
        assertEquals("Check if password changed or not", 1, actual);
    }

    public void prepareTestData(){
        userInfo = new UserInfo("jainy@arezzosky.com", "jainy");
        expectedUser = new User();
        expectedUser.setName("Yash Jain");
        expectedUser.setUsername("jainy");
        expectedUser.setEmail("jainy@arezzosky.com");
        expectedUser.setId(1l);
        expectedUser.setPassword("Yash@123");
        authKeys = new AuthKeys();
        authKeys.setSecretKey("1234567891234567");
    }
}
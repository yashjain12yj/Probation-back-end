package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.ChangePasswordDTO;
import com.buynsell.buynsell.payload.DashboardDTO;
import com.buynsell.buynsell.payload.UserInfo;
import com.buynsell.buynsell.service.UserProfileService;
import com.buynsell.buynsell.service.UserService;
import com.buynsell.buynsell.util.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/private/user")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileValidator userProfileValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthKeys authKeys;

    @Autowired
    private AuthenticationTokenUtil authenticationTokenUtil;

    @Autowired
    UserInfo userInfo;

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        String usernameOrEmail = userInfo.getEmail();
        Optional<User> user = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        ResponseEntity responseEntity = userProfileValidator.changePasswordValidator(changePasswordDTO);
        if (responseEntity != null)
            return responseEntity;
        int res = userProfileService.changePassword(user.get(), changePasswordDTO);
        if (res == 1)
            return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
        else if (res == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your current password is wrong!");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");

    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        String usernameOrEmail = userInfo.getEmail();
        Optional<User> user = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        DashboardDTO dashboardDTO = userProfileService.getDasboard(user.get());
        return ResponseEntity.status(HttpStatus.OK).body(dashboardDTO);
    }
}

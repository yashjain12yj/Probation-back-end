package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.payload.ChangePasswordDTO;
import com.buynsell.buynsell.payload.DashboardDTO;
import com.buynsell.buynsell.payload.UserInfo;
import com.buynsell.buynsell.service.UserProfileService;
import com.buynsell.buynsell.util.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/private/user")
public class
UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileValidator userProfileValidator;

    @Autowired
    UserInfo userInfo;

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        ResponseEntity responseEntity = userProfileValidator.changePasswordValidator(changePasswordDTO);
        if (responseEntity != null)
            return responseEntity;
        int res = 0;
        try {
            res = userProfileService.changePassword(changePasswordDTO);
            if (res == 0)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your current password is wrong!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        DashboardDTO dashboardDTO = userProfileService.getDashboard();
        if (dashboardDTO == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        return ResponseEntity.status(HttpStatus.OK).body(dashboardDTO);
    }
}

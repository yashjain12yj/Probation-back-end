package com.buynsell.buynsell.util;

import com.buynsell.buynsell.payload.ChangePasswordDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserProfileValidator {
    public ResponseEntity changePasswordValidator(ChangePasswordDTO changePasswordDTO){
        String oldPassword = changePasswordDTO.getOldPassword();
        if (oldPassword == null || oldPassword.trim().equals(""))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old Password required!");
        if (oldPassword.trim().length() < 8 || oldPassword.trim().length() > 20)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old Password length should be 8 to 20 characters long.");
        String newPassword = changePasswordDTO.getNewPassword();
        if (newPassword == null || newPassword.equals(""))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password required");
        else if (newPassword.length() < 8 || newPassword.length() > 20)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 6 to 20 characters long");
        boolean hasLower = false, hasUpper = false, hasDigit = false, hasSpecialCHr = false;
        for (char c : newPassword.toCharArray()) {
            if (Character.isDigit(c))
                hasDigit = true;
            else if (Character.isLowerCase(c))
                hasLower = true;
            else if (Character.isUpperCase(c))
                hasUpper = true;
            else if (c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '&' || c == '^' || c == '*' || c == '(' || c == ')' || c == '-' || c == '+')
                hasSpecialCHr = true;
        }
        if (!(hasDigit && hasLower && hasSpecialCHr && hasUpper))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New Password atleast contain one lowercase, one uppercase, one digit and one special character");
        String confirmNewPassword = changePasswordDTO.getConfirmNewPassword();
        if (!newPassword.equals(confirmNewPassword))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password and confirm password must be equal");
        if (oldPassword.equals(newPassword))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old Password is same as new password.");
        return null;
    }
}

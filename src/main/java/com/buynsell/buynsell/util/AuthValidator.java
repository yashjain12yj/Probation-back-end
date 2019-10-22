package com.buynsell.buynsell.util;

import com.buynsell.buynsell.payload.LoginRequest;
import com.buynsell.buynsell.payload.SignUpRequest;
import com.buynsell.buynsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class AuthValidator {

    @Autowired
    UserService userService;

    public ResponseEntity validateSignin(LoginRequest loginRequest) {
        if (loginRequest.getUsernameOrEmail() == null || loginRequest.getUsernameOrEmail().trim().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or Email is required");
        } else if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is required");
        }
        return null;
    }

    public ResponseEntity validateSignup(SignUpRequest signUpRequest) {
        String name = signUpRequest.getName().trim();
        if (name == null || name.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is required");
        } else if (name.length() < 4 || name.length() > 40) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name must be 4 to 40 character long");
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name should not contain any digit or special character");
            }
        }

        String username = signUpRequest.getUsername().trim();
        if (username == null || username.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required");
        } else if (username.length() < 4 || username.length() > 20) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username must be 4 to 20 characters long");
        }
        for (char c : username.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username should only contain characters and digits");
            }
        }

        String email = signUpRequest.getEmail();
        if (email == null || email.trim().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email required");
        } else if (email.trim().length() > 30) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email must be less than 30 characters");
        }
        Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher regMatcher = regexPattern.matcher(email);
        if (!regMatcher.matches())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Email");

        String password = signUpRequest.getPassword();
        if (password == null || password.trim().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password required");
        } else if (password.trim().length() < 8 || password.trim().length() > 20) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 6 to 20 characters long");
        }
        boolean hasLower = false, hasUpper = false, hasDigit = false, hasSpecialCHr = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
                //!@#$%^&*()-+
            } else if (c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '&' || c == '^' || c == '*' || c == '(' || c == ')' || c == '-' || c == '+') {
                hasSpecialCHr = true;
            }
        }
        if (!(hasDigit && hasLower && hasSpecialCHr && hasUpper))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password much contain one lowercase, one uppercase, one digit and one special character");

        String confirmPassword = signUpRequest.getConfirmPassword().trim();
        if (!password.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password and confirm password must be equal");
        }


        return null;
    }


}

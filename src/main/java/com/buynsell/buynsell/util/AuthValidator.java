package com.buynsell.buynsell.util;

import com.buynsell.buynsell.payload.LoginRequest;
import com.buynsell.buynsell.payload.SignUpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class AuthValidator {

    public Optional<String> validateSignin(LoginRequest loginRequest) {
        if (StringUtils.isEmpty(loginRequest.getUsernameOrEmail()))
            return Optional.of("Username or Email is required");
        else if (StringUtils.isEmpty(loginRequest.getPassword()))
            return Optional.of("Password is required");
        return Optional.empty();
    }

    public Optional<String> validateName(String name){
        if (StringUtils.isEmpty(name))
            return Optional.of("Name is required");
        else if (name.length() < 4 || name.length() > 40)
            return Optional.of("Name must be 4 to 40 character long");
        for (char c : name.toCharArray())
            if (!Character.isLetter(c) && c != ' ')
                return Optional.of("Name should not contain any digit or special character");
        return Optional.empty();
    }

    public Optional<String> validateUsername(String username){
        if (StringUtils.isEmpty(username))
            return Optional.of("Username is required");
        else if (username.trim().length() < 4 || username.trim().length() > 20)
            return Optional.of("Username must be 4 to 20 characters long");
        for (char c : username.trim().toCharArray())
            if (!Character.isLetter(c) && !Character.isDigit(c))
                return Optional.of("Username should only contain characters and digits");
        return Optional.empty();
    }
    public Optional<String> validateEmail(String email){
        if (StringUtils.isEmpty(email))
            return Optional.of("Email required");
        else if (email.trim().length() > 30)
            return Optional.of("Email must be less than 30 characters");
        Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher regMatcher = regexPattern.matcher(email);
        if (!regMatcher.matches())
            return Optional.of("Invalid Email");
        return Optional.empty();
    }
    public Optional<String> validatePassword(String password){
        if (StringUtils.isEmpty(password))
            return Optional.of("Password required");
        else if (password.trim().length() < 8 || password.trim().length() > 20)
            return Optional.of("Password must be 8 to 20 characters long");
        boolean hasLower = false, hasUpper = false, hasDigit = false, hasSpecialCHr = false;
        for (char c : password.trim().toCharArray()) {
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
            return Optional.of("Password much contain one lowercase, one uppercase, one digit and one special character");
        return Optional.empty();
    }

    public Optional<String> validateConfirmPassword(String password, String confirmPassword){
        if (!password.equals(confirmPassword))
            return Optional.of("Password and confirm password must be equal");
        return Optional.empty();
    }

    public Optional<String> validateSignup(SignUpRequest signUpRequest) {
        Optional<String> validationErrorMessage = validateName(signUpRequest.getName());
        if (validationErrorMessage.isPresent()) return validationErrorMessage;

        validationErrorMessage = validateUsername(signUpRequest.getUsername());
        if (validationErrorMessage.isPresent()) return validationErrorMessage;

        validationErrorMessage = validateEmail(signUpRequest.getEmail());
        if (validationErrorMessage.isPresent()) return validationErrorMessage;

        validationErrorMessage = validatePassword(signUpRequest.getPassword());
        if (validationErrorMessage.isPresent()) return validationErrorMessage;

        validationErrorMessage = validateConfirmPassword(signUpRequest.getPassword(),signUpRequest.getConfirmPassword());
        if (validationErrorMessage.isPresent()) return validationErrorMessage;

        return Optional.empty();
    }
}

package com.buynsell.buynsell.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank
    @Size(min = 6, max = 20, message = "Password must be between 6 to 20 size")
    private String password;

    @NotBlank
    @Size(min = 6, max = 20, message = "Password must be between 6 to 20 size")
    private String confirmPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isValid() {

        // Name should contain only characters i.e., a-zA-Z
        String name = this.getName().trim();
        if (name.length() < 3 || name.length() > 20) return false;
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }

        // username validation
        String username = this.getUsername().trim();
        if (username.length() < 3 || username.length() > 20) return false;
        for (char c : username.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return false;
            }
        }

        // Email Validation


        // Password Validation
        String password = this.getPassword().trim();
        if (password.length() < 6 || password.length() > 20) return false;

        // Confirm Password Validation
        String confirmPassword = this.getConfirmPassword().trim();
        if (confirmPassword.length() < 6 || confirmPassword.length() > 20) return false;

        // compare password
        if (!password.equals(confirmPassword)) return false;

        return true;
    }
}

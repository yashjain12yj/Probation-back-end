package com.buynsell.buynsell.payload;

public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public ChangePasswordDTO(String oldPassword, String newPassword, String confirmNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public ChangePasswordDTO() {
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

}

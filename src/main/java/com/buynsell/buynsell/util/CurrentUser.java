package com.buynsell.buynsell.util;

import com.buynsell.buynsell.model.User;

public class CurrentUser {

    private static User currentUser;

    private CurrentUser() {

    }

    public static User getCurrentUser() {
        if (currentUser == null) {
            synchronized (CurrentUser.class) {
                if (currentUser == null) {
                    currentUser = new User();
                }
            }
        }
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        User currentUser = getCurrentUser();
        currentUser.setId(user.getId());
        currentUser.setName(user.getName());
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());
        currentUser.setActive(user.isActive());
        currentUser.setItems(user.getItems());
        currentUser.setCreatedAt(user.getCreatedAt());
        currentUser.setUpdatedAt(user.getUpdatedAt());
        currentUser.setPassword(user.getPassword());
    }

}

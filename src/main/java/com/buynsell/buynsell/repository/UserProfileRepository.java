package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.ChangePasswordDTO;
import com.buynsell.buynsell.payload.DashboardDTO;
import com.buynsell.buynsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;


@Repository
public class UserProfileRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthKeys authKeys;

    @Autowired
    private UserService userService;

    /**
     * @param user
     * @return -1 if database error, 1 if successfully changed password, 0 if password doesn't match.
     */
    @Transactional
    public int changePassword(User user, String newPassword) {
        try {
            user.setPassword(newPassword);
            entityManager.merge(user);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }


    public User getDashboard(String username) {
        Optional<User> user = userService.findByUsernameOrEmail(username);
        if (!user.isPresent()) return null;
        return user.get();
    }
}

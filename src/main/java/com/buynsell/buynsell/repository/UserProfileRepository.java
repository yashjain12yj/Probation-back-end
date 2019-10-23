package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.ChangePasswordDTO;
import com.buynsell.buynsell.payload.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Set;


@Repository
public class UserProfileRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthKeys authKeys;
    /**
     *
     * @param user
     * @param changePasswordDTO
     * @return -1 if database error, 1 if successfully changed password, 0 if password doesn't match.
     */
    @Transactional
    public int changePassword(User user, ChangePasswordDTO changePasswordDTO){
        if (user == null)
            return 0;
        String oldPassword = AESEncryption.encrypt(changePasswordDTO.getOldPassword(), authKeys.getSecretKey());
        if (!(user.getPassword().equals(oldPassword)))
            return 0;
        user.setPassword(AESEncryption.encrypt(changePasswordDTO.getNewPassword(), authKeys.getSecretKey()));
        entityManager.merge(user);
        return 1;
    }


    public DashboardDTO getDashboard(User user){
        DashboardDTO dashboardDTO = new DashboardDTO();
        ArrayList<Item> items = new ArrayList<>();
        for(Item item: user.getItems()){
            item.setUser(null);
            Set<Image> images;
            images = item.getImages();
            for(Image image : images)
                image.setItem(null);
            items.add(item);
        }
        dashboardDTO.setItems(items);
        return dashboardDTO;
    }
}

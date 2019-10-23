package com.buynsell.buynsell.service;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.ChangePasswordDTO;
import com.buynsell.buynsell.payload.DashboardDTO;
import com.buynsell.buynsell.payload.UserInfo;
import com.buynsell.buynsell.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserInfo userInfo;

    @Autowired
    private AuthKeys authKeys;

    @Autowired
    private UserService userService;

    @Transactional
    public int changePassword(ChangePasswordDTO changePasswordDTO){
        Optional<User> user = userService.findByUsernameOrEmail(userInfo.getEmail());
        if (!user.isPresent()) return -1;

        if(!user.get().getPassword().equals(AESEncryption.encrypt(changePasswordDTO.getOldPassword(), authKeys.getSecretKey()))) return 0;

        return userProfileRepository.changePassword(user.get(), AESEncryption.encrypt(changePasswordDTO.getNewPassword(), authKeys.getSecretKey()));
    }

    public DashboardDTO getDashboard(){
        User user = userProfileRepository.getDashboard(userInfo.getUsername());
        if (user == null) return null;
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

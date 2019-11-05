package com.buynsell.buynsell.service;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.ChangePasswordDTO;
import com.buynsell.buynsell.payload.DashboardDTO;
import com.buynsell.buynsell.model.UserInfo;
import com.buynsell.buynsell.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public int changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        Optional<User> user = userService.findByUsernameOrEmail(userInfo.getEmail());
        if (!user.isPresent()) return -1;

        if (!user.get().getPassword().equals(AESEncryption.encrypt(changePasswordDTO.getOldPassword(), authKeys.getSecretKey())))
            return 0;
        userProfileRepository.changePassword(user.get(), AESEncryption.encrypt(changePasswordDTO.getNewPassword(), authKeys.getSecretKey()));
        return 1;
    }

    public DashboardDTO getDashboard() throws Exception {
        List<Item> items = userProfileRepository.getDashboard(userInfo.getUsername());
        DashboardDTO dashboardDTO = new DashboardDTO();
        ArrayList<Item> newItems = new ArrayList<>();
        for (Item item : items) {
            item.setUser(null);
            newItems.add(item);
        }
        dashboardDTO.setItems(newItems);
        return dashboardDTO;
    }


}

package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.ChangePasswordDTO;
import com.buynsell.buynsell.payload.DashboardDTO;
import com.buynsell.buynsell.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;
    public int changePassword(User user, ChangePasswordDTO changePasswordDTO){
        return userProfileRepository.changePassword(user, changePasswordDTO);
    }

    public DashboardDTO getDasboard(User user){
        return userProfileRepository.getDashboard(user);
    }


}

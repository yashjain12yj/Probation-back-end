package com.buynsell.buynsell.encryption;

import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationTokenUtil {

    @Autowired
    UserService userService;

    @Autowired
    AuthKeys authKeys;

    @Autowired
    AuthenticationTokenUtil authenticationTokenUtil;

    public String generateToken(String usernameOrEmail, String secretKey){
        return AESEncryption.encrypt(usernameOrEmail, secretKey);
    }

    public String getUsernameOrEmailFromToken(String token, String secretKey){
        return AESEncryption.decrypt(token, secretKey);
    }

    public Optional<User> getUserFromHeader(HttpHeaders headers){
        if (headers.get("token") != null) {
            String token = headers.get("token").get(0);

            String usernameOrEmail = authenticationTokenUtil.getUsernameOrEmailFromToken(token, authKeys.getTokenSecretKey());

            // get user
            return userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        }
        return Optional.empty();
    }
}

package com.buynsell.buynsell.encryption;

import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public class AuthenticationTokenUtil {

    @Autowired
    static UserService userService;

    @Autowired
    static AuthKeys authKeys;

    public static String generateToken(String usernameOrEmail, String secretKey){
        return AESEncryption.encrypt(usernameOrEmail, secretKey);
    }

    public static String getUsernameOrEmailFromToken(String token, String secretKey){
        return AESEncryption.decrypt(token, secretKey);
    }

    public static Optional<User> getUserFromHeader(HttpHeaders headers){
        if (headers.get("token") != null) {
            String token = headers.get("token").get(0);

            String usernameOrEmail = AuthenticationTokenUtil.getUsernameOrEmailFromToken(token, authKeys.getTokenSecretKey());

            // get user
            return userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        }
        return Optional.empty();
    }
}

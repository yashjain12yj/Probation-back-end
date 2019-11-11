package com.buynsell.buynsell.encryption;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationTokenUtil {

    public String generateToken(String usernameOrEmail, String secretKey) throws Exception {
        return AESEncryption.encrypt(usernameOrEmail, secretKey);
    }

    public String getUsernameOrEmailFromToken(String token, String secretKey) throws Exception {
        return AESEncryption.decrypt(token, secretKey);
    }
}

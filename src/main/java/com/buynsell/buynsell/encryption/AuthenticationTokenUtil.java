package com.buynsell.buynsell.encryption;

public class AuthenticationTokenUtil {
    public static String generateToken(String usernameOrEmail, String secretKey){
        return AESEncryption.encrypt(usernameOrEmail, secretKey);
    }

    public static String getUsernameOrEmailFromToken(String token, String secretKey){
        return AESEncryption.decrypt(token, secretKey);
    }
}

package com.buynsell.buynsell.encryption;

import org.springframework.beans.factory.annotation.Value;

public class AuthKeys {

    @Value("${secretKey}")
    private static String secretKey;

    @Value("${tokenSecretKey}")
    private static String tokenSecretKey;

    public static String getSecretKey() {
        return secretKey;
    }

    public static String getTokenSecretKey() {
        return tokenSecretKey;
    }
}

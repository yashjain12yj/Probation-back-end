package com.buynsell.buynsell.encryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthKeys {

    @Value("${secretKey}")
    private String secretKey;

    @Value("${tokenSecretKey}")
    private String tokenSecretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public String getTokenSecretKey() {
        return tokenSecretKey;
    }
}

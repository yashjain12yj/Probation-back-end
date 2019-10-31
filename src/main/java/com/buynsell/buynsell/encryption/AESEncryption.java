package com.buynsell.buynsell.encryption;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


/* Key must be of length 16, 24 or 32*/
public class AESEncryption {

    private static final String ALGO = "AES";

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param text is a string
     * @return the encrypted string
     */
    public static String encrypt(String text, String secretKey) throws Exception {
        Key key = new SecretKeySpec(secretKey.getBytes(), ALGO);
        Cipher c = null;
        c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = new byte[0];
        encVal = c.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData, String secretKey) throws Exception {
        Key key = new SecretKeySpec(secretKey.getBytes(), ALGO);
        Cipher c = null;
        c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = new byte[0];
        decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }

    /* Just for testing purpose */
    public static void main(String[] args) {

        String key = "1234567891234567";
        String text = "password";

        System.out.println("Plain test: " + text);

        String encryptedText = null;
        try {
            encryptedText = encrypt(text, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Encrypted text: " + encryptedText);
        try {
            System.out.println("Decrypted text: " + decrypt(encryptedText, key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

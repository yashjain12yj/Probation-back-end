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
    public static String encrypt(String text, String secretKey) {
        Key key = new SecretKeySpec(secretKey.getBytes(), ALGO);
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGO);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            c.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] encVal = new byte[0];
        try {
            encVal = c.doFinal(text.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(encVal);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData , String secretKey)  {
        Key key = new SecretKeySpec(secretKey.getBytes(), ALGO);
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGO);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            c.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = new byte[0];
        try {
            decValue = c.doFinal(decordedValue);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return new String(decValue);
    }

    /* Just for testing purpose */
    public static void main(String[] args) {

        String key = "1234567891234567";
        String text = "password";

        System.out.println("Plain test: " + text);

        String encryptedText = encrypt(text, key);
        System.out.println("Encrypted text: " + encryptedText);
        System.out.println("Decrypted text: " + decrypt(encryptedText, key));
    }
}

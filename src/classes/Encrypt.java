package classes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * class focuses on encrypting and decrypting password
 */
public class Encrypt {
    /**
     * secretKey is the set Key
     */
    private static final String secretKey = "ssshhhhhhhhhhh!!!!";
    private static SecretKeySpec secretKeySpec;
    private static byte[] key;

    public static void setKey(String myKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        key = myKey.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");

        //.digest completes hash comp like padding
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKeySpec = new SecretKeySpec(key, "AES");
    }

    public static String encrypt(String toEncrypt) throws UnsupportedEncodingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        setKey(secretKey);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(toEncrypt.getBytes("UTF-8")));
    }

    public static String decrypt(String toDecrypt) throws UnsupportedEncodingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        setKey(secretKey);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(toDecrypt)));
    }

    public static String getKey() {
        return secretKey;
    }
}
package hashing;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hashing {
    
    public static String md5Hashing(String str) throws Exception {
        MessageDigest instance = getDigest("MD5");
        byte[] digest = digest(instance, str);
        
        return toHexHash(digest);
    }
    
    public static String sha256Hashing(String str) throws Exception {
        MessageDigest instance = getDigest("SHA-256");
        byte[] digest = digest(instance, str);
        
        return toHexHash(digest);
    }
    
    public static String sha512Hashing(String str) throws Exception {
        MessageDigest instance = getDigest("SHA-512");
        byte[] digest = digest(instance, str);
        
        return toHexHash(digest);
    }
    
    public static String md5HashingWithSalt(String str, String salt) throws Exception {
        MessageDigest instance = getDigest("MD5");
        byte[] digest = digestWithSalt(instance, str, salt);
        
        return toHexHash(digest);
    }
    
    public static String sha256HashingWithSalt(String str, String salt) throws Exception {
        MessageDigest instance = getDigest("SHA-256");
        byte[] digest = digestWithSalt(instance, str, salt);
        
        return toHexHash(digest);
    }
    
    public static String sha512HashingWithSalt(String str, String salt) throws Exception {
        MessageDigest instance = getDigest("SHA-512");
        byte[] digest = digestWithSalt(instance, str, salt);
        
        return toHexHash(digest);
    }
    
    public static String hashOverHash(String algoritmo, int numberOfIterations) {
    }

    public static String hashOverHashWithSalt(String algoritmo, int numberOfIterations) {
        
    }

    private static MessageDigest getDigest(String algoritmo) {
        return MessageDigest.getInstance(algoritmo.toUpperCase());
    }
    
    private static String toHexHash(byte[] hash) {
        return String.format("%x", new BigInteger(1, hash));
    }
    
    private static MessageDigest addSalt(MessageDigest digest, String salt) {
        digest.update(salt.getBytes(CHARSET));
        return digest;
    }
    
    private static byte[] digest(MessageDigest digest, String str) {
        digest.digest(str.getBytes(CHARSET));
    }

    private static byte[] digestWithSalt(MessageDigest digest, String str, String salt) {
        Hashing.addSalt(digest, salt);
        digest.digest(str.getBytes(CHARSET));
    }
    
    
    public static void main(String[] args) throws Exception {
        String plainText = "TESTE";
        
        System.out.println("Plain Text = " + plainText);
        System.out.println("MD5 = " + Hashing.md5Hashing(plainText));
        System.out.println("SHA-256 = " + Hashing.sha256Hashing(plainText));
        System.out.println("SHA-512 = " + Hashing.sha512Hashing(plainText));

        String salt = "SaltTest";

        System.out.println("MD5 with Salt = " + Hashing.md5HashingWithSalt(plainText, salt));
        System.out.println("SHA-256 with Salt = " + Hashing.sha256HashingWithSalt(plainText, salt));
        System.out.println("SHA-512 with Salt = " + Hashing.sha512HashingWithSalt(plainText, salt));
    }
    
    private static final String CHARSET = "UTF-8";

}

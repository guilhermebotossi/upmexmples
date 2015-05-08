package hashing;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hashing {
	
	public static String md5Hashing(String str) throws Exception {
		MessageDigest instance = MessageDigest.getInstance("MD5");
		byte[] digest = instance.digest(str.getBytes(CHARSET));
		
		return toHexHash(digest);
	}
	
	public static String sha256Hashing(String str) throws Exception {
		MessageDigest instance = MessageDigest.getInstance("SHA-256");
		byte[] digest = instance.digest(str.getBytes(CHARSET));
		
		return toHexHash(digest);
	}
	
	public static String sha512Hashing(String str) throws Exception {
		MessageDigest instance = MessageDigest.getInstance("SHA-512");
		byte[] digest = instance.digest(str.getBytes(CHARSET));
		
		return toHexHash(digest);
	}
	
	private static String toHexHash(byte[] hash) {
		return String.format("%x", new BigInteger(1, hash));
	}
	
	
	public static void main(String[] args) throws Exception {
		String plainText = "TESTE";
		
		System.out.println("Plain Text = " + plainText);
		System.out.println("MD5 = " + Hashing.md5Hashing(plainText));
		System.out.println("SHA-256 = " + Hashing.sha256Hashing(plainText));
		System.out.println("SHA-512 = " + Hashing.sha512Hashing(plainText));
	}
	
	private static final String CHARSET = "UTF-8";

}

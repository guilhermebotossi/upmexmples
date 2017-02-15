package br.com.tmf.generator;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.UUID;

import javax.ejb.Stateless;

@Stateless
public class IDGenerator {
	
	
	public synchronized String generateUUID() {
		return UUID.randomUUID().toString();	
	}
	
	public String generateMD5HashId() throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		return toHexHash(digest.digest());	
	}
	
	public String generateMD5HashIdSalt(String salt) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(salt.getBytes(CHARSET));
		return toHexHash(digest.digest());	
	}
	
    public static String toHexHash(byte[] hash) {
        return String.format("%x", new BigInteger(1, hash));
    }
    
    public String secureSHA1Hash() throws Exception{
        SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

        //generate a random number
        String randomNum = new Integer(prng.nextInt()).toString();

        //get its digest
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(randomNum.getBytes());
        byte[] result =  digest.digest();
    	
    	return toHexHash(result);
    }
    
    public String secureSHA1HashWithSalt(String salt) throws Exception{
        SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

        //generate a random number
        String randomNum = new Integer(prng.nextInt()).toString();

        //get its digest
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update((salt+randomNum).getBytes());
        byte[] result =  digest.digest();
    	
    	return toHexHash(result);
    }

	private static final String CHARSET = "UTF-8";
	
		
}

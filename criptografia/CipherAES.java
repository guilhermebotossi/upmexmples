package criptografia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class CipherAES {
	
	public static String encrypt(String key1, String key2, String str) throws Exception {
		
        IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));

        SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"), "AES");
		
			
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");		
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);		
		
		byte[] encrypted = cipher.doFinal(str.getBytes());
		
		return Base64.encode(encrypted);
	}
	
    public static String decrypt(String key1, String key2, String encrypted) throws Exception {
            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));

            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decode(encrypted));

            return new String(original);
    }
    
    public static void main(String[] args) throws Exception {

        String key1 = "Teste123TESTE456"; // 128 bit key
        String key2 = "TesTEdESeGUraNca";// 16 bytes
        
		String file = args[0];
		
		FileReader fr = new FileReader(new File(file)); 
		BufferedReader br = new BufferedReader(fr);
		
		StringBuilder sb = new StringBuilder();
		String linha = null;
		
		 while((linha = br.readLine()) != null ) {
			 sb.append(linha);
		 }
		 
		 br.close();
		 
		 String str = sb.toString();
		
		str = encrypt(key1, key2, str);
		System.out.println("texto encriptado AES : " + str);
		
		str = decrypt(key1, key2, str);
		System.out.println("texto decriptado AES : " + str);
    }
}

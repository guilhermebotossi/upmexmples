package br.com.tmf.ws;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.engines.AESWrapEngine;

import br.com.tmf.vo.CryptWsVO;

@WebService(name = "WSCrypto")
@Stateless
@SuppressWarnings("unused")
public class WSCrypto {
	
	@PostConstruct
	private void init(){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	@WebMethod(operationName = "encrypt")
	public CryptWsVO encrypt(@WebParam(name = "conteudo") String str) throws Exception {
		SecureRandom random = new SecureRandom();
		PrivateKey pk = (PrivateKey) loadKey(PRIVATE_KEY_PATH);
		Key aesKey = createKeyForAES(256, random);
		Cipher cipherAES = getBouncyCastleCipher(CIFRA_AES);
		
		
		cipherAES.init(Cipher.ENCRYPT_MODE, aesKey);		
		byte[] cipherText = cipherAES.doFinal(str.getBytes());
		byte[] wrappedKey = wrapKey(pk, aesKey);
		SealedObject sealedObject = new SealedObject(wrappedKey, cipherAES);
		return toWsResponse(wrappedKey, Base64.encodeBase64String(cipherText));
	}
	@WebMethod(operationName = "decrypt")
	public String decrypt(@WebParam(name = "conteudo") CryptWsVO vo) throws Exception {
		PublicKey pk = (PublicKey) loadKey(PUBLIC_KEY_PATH);
		Cipher cipher = getBouncyCastleCipher(CIFRA_AES);
		byte[] wrappedKey = vo.getSymmetricKey();
		Key key = unwrapKey(wrappedKey, pk);
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] original = cipher.doFinal(Base64.decodeBase64(vo.getEncryptedContent()));
	    return new String(original);
	}
	
	
	private Key loadKey(String path) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
	    Key key = (Key) ois.readObject();
	    
	    ois.close();
 		
 		return key;
	}
	
	
	
	private byte[] wrapKey(PrivateKey pk, Key aesKey) throws Exception {
		Cipher cipher = getWrapCipher(pk, Cipher. WRAP_MODE);
		    cipher.init(Cipher.WRAP_MODE, pk);
		    return cipher.wrap(aesKey);
  }
	
	private Key unwrapKey(byte[] wrappedKey, PublicKey pk) throws Exception {
		Cipher cipher = getWrapCipher(pk, Cipher.UNWRAP_MODE);
	    return cipher.unwrap(wrappedKey, ALGORTIMO_RSA, Cipher.SECRET_KEY);
	}
	
	private Cipher getWrapCipher(Key pk, int wrappingMode)throws Exception {
		Cipher cipher = getBouncyCastleCipher(CIFRA_RSA);
	    cipher.init(wrappingMode, pk);
	    return cipher;
	}

	
	private static Key createKeyForAES(int i, SecureRandom random)  throws Exception {
	    KeyGenerator generator = KeyGenerator.getInstance(CIFRA_AES, "BC");
	    generator.init(i, random);
	    return generator.generateKey();
	}
	
	private void generateRSAKeyPair() {
	    try {
		      final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORTIMO_RSA);
		      keyGen.initialize(2048);
		      final KeyPair key = keyGen.generateKeyPair();

		      File privateKeyFile = new File(PRIVATE_KEY_PATH);
		      File publicKeyFile = new File(PUBLIC_KEY_PATH);

		      // Create files to store public and private key
		      if (privateKeyFile.getParentFile() != null) {
		        privateKeyFile.getParentFile().mkdirs();
		      }
		      privateKeyFile.createNewFile();

		      if (publicKeyFile.getParentFile() != null) {
		        publicKeyFile.getParentFile().mkdirs();
		      }
		      publicKeyFile.createNewFile();

		      // Saving the Public key in a file
		      ObjectOutputStream publicKeyOS = new ObjectOutputStream(
		          new FileOutputStream(publicKeyFile));
		      publicKeyOS.writeObject(key.getPublic());
		      publicKeyOS.close();

		      // Saving the Private key in a file
		      ObjectOutputStream privateKeyOS = new ObjectOutputStream(
		          new FileOutputStream(privateKeyFile));
		      privateKeyOS.writeObject(key.getPrivate());
		      privateKeyOS.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	
	private CryptWsVO toWsResponse(byte[] key , String content) {
		return new CryptWsVO(key, content);
	}
	
	private Cipher getBouncyCastleCipher(String alg) throws Exception {
		return Cipher.getInstance(alg, "BC");
	}
	
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		WSCrypto cript = new WSCrypto();
		String path = "C:\\Users\\guilherme.botossi\\AppData\\Roaming\\Skype\\My Skype Received Files\\NFe(2)\\3515059673489200039555012000000001.xml";
		
		BufferedReader br = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        
        String everything = sb.toString();
        br.close();
		
		String str = sb.toString();
	    System.out.println("Tamanho antes = " + str.length());
	    CryptWsVO vo = cript.encrypt(str);
	    
	    System.out.println("Tamanho pos encriptação = " + vo.getEncryptedContent().length());
	    
	    String str2 = cript.decrypt(vo);
	    System.out.println("Tamanho depois = " + str.length());
	    
	    System.out.println(str.equals(str2));
	}

	
	private static final String PRIVATE_KEY_PATH = "C:/eclipse/secur/chave";
	private static final String PUBLIC_KEY_PATH = "C:/eclipse/secur/chave.pub";
	private static final String CIFRA_RSA = "RSA/ECB/PKCS1Padding";
	private static final String ALGORTIMO_RSA = "RSA";
	private static final String CIFRA_AES = "AES";
	
	
}

package br.com.tmf.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CryptWsVO implements Serializable{
	
	public CryptWsVO(byte[] symmetricKey, String encryptedContent) {
		this.symmetricKey = symmetricKey;
		this.encryptedContent = encryptedContent;
	}
	
	public CryptWsVO() {}

	private static final long serialVersionUID = -2293924863906322666L;
	
	private byte[] symmetricKey;
	
	private String encryptedContent;


	public String getEncryptedContent() {
		return encryptedContent;
	}

	public void setEncryptedContent(String encryptedContent) {
		this.encryptedContent = encryptedContent;
	}

	public byte[] getSymmetricKey() {
		return symmetricKey;
	}

	public void setSymmetricKey(byte[] symmetricKey) {
		this.symmetricKey = symmetricKey;
	}
	
	

}

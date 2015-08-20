package br.com.tmf.ws;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.com.tmf.generator.IDGenerator;
import br.com.tmf.jedis.JedisManager;

@WebService(name = "WSEntrada")
@Stateless
public class WSEntrada {
	
	@EJB(mappedName = "JedisManager/no-interface")
	private JedisManager jedisManager;
	
	@EJB(mappedName = "IDGenerator/no-interface")
	private IDGenerator generator;

	@WebMethod(operationName = "registerUUID")
	public String protocolUUID(@WebParam(name="nome")String nome, @WebParam(name="cnpj")String cnpj, @WebParam(name="ie")String ie) {
		int ttl = 1800;
		String hash = generator.generateUUID();
		jedisManager.setToJediswithTTL(hash, ttl);
		
	    return toXMLRegistro(hash, ttl);
	}
	
	@WebMethod(operationName = "register")
	public String protocol(@WebParam(name="nome")String nome, @WebParam(name="cnpj")String cnpj, @WebParam(name="ie")String ie) throws Exception {
		int ttl = 1800;
		String hash = generator.generateMD5HashId();
		jedisManager.setToJediswithTTL(hash, ttl);
		
	    return toXMLRegistro(hash, ttl);
	}
	
	@WebMethod(operationName = "registerWithSalt")
	public String protocolWithSalt(@WebParam(name="nome")String nome, @WebParam(name="cnpj")String cnpj, @WebParam(name="ie")String ie) throws Exception {
		int ttl = 1800;
		Date data = new Date();
		String hash = generator.generateMD5HashIdSalt(nome+cnpj+ie+data.getTime());
		jedisManager.setToJediswithTTL(hash, ttl);
		
	    return toXMLRegistro(hash, ttl);
	}
	
	@WebMethod(operationName = "secureRegister")
	public String secureProtocol(@WebParam(name="nome")String nome, @WebParam(name="cnpj")String cnpj, @WebParam(name="ie")String ie) throws Exception {
		int ttl = 2000;
		String hash = generator.secureSHA1Hash();
		jedisManager.setToJediswithTTL(hash, ttl);
		
	    return toXMLRegistro(hash, ttl);
	}
	
	@WebMethod(operationName = "secureRegisterWithSalt")
	public String secureProtocolWithSalt(@WebParam(name="nome")String nome, @WebParam(name="cnpj")String cnpj, @WebParam(name="ie")String ie) throws Exception {
		int ttl = 1800;
		String hash = generator.secureSHA1HashWithSalt(nome+cnpj+ie);
		jedisManager.setToJediswithTTL(hash, ttl);
		
	    return toXMLRegistro(hash, ttl);
	}
	
	@WebMethod(operationName = "ConsultaProtocolo")
	public String consulta(@WebParam(name = "protocolo")String hash) throws Exception {
		String msg = "";
		if(jedisManager.getStringIfExists(hash) != null) {
			long ttl = jedisManager.ttlKey(hash);
			msg = "O protocolo ainda não foi aprovado";
			return toXMLConsulta(hash, ttl, msg);
		}
		msg = "Protocolo Aprovado!";
		return toXMLConsulta(hash, null, msg);
	}
	
	
	private String toXMLRegistro(String hash, Integer ttl) {
		StringBuilder sb = new StringBuilder();
		sb.append("<registro>");
		sb.append("<protocolo>").append(hash).append("</protocolo>");
		sb.append("<ttl>").append(ttl).append("</ttl>");
		sb.append("<data>").append(Calendar.getInstance().getTime().toString()).append("</data>");
		sb.append("</registro>");
		
		return sb.toString();
	}
	
	private String toXMLConsulta(String hash, Long ttl, String msg) {
		StringBuilder sb = new StringBuilder();
		sb.append("<consulta>");
		sb.append("<msg>").append(msg).append("</msg>");
		sb.append("<protocolo>").append(hash).append("</protocolo>");
		if(ttl != null) {
			sb.append("<tempoEstimadoAprovacao>").append(ttl).append("</tempoEstimadoAprovacao>");
		}
		sb.append("<data>").append(Calendar.getInstance().getTime().toString()).append("</data>");
		sb.append("</consulta>");
		
		return sb.toString();
	}
	
}

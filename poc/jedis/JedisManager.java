package br.com.tmf.jedis;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import redis.clients.jedis.Jedis;

@Stateless
public class JedisManager {
	
	@EJB(mappedName = "RedisSingletonManager/no-interface")
	private RedisSingletonManager manager;
	
	/**
	 * 
	 * @param str
	 * @param ttl - Time to Live
	 */
	public void setToJediswithTTL(String str, int ttl) {
		jedis = manager.getInstance();
		jedis.set(str, "", "NX", "EX", ttl);
		jedis.close();
	}
	
	public void  setToJedis(String str) {
		jedis = manager.getInstance();
		jedis.set(str, "");
		jedis.close();
	}
	
	public String getStringIfExists(String str) {
		jedis = manager.getInstance();
		str = jedis.get(str);
		jedis.close();
		
		return str; 
	}
	
	public Long ttlKey(String key) {
		jedis = manager.getInstance();
		long ttl = jedis.ttl(key);
		jedis.close();
		
		return ttl;
	}
	
	private Jedis jedis ;
	
}

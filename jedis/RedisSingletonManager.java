package br.com.tmf.jedis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Singleton
public class RedisSingletonManager {

	@PostConstruct
	private void init() {
		createJedisPool();
	}
	
	@PreDestroy
	private void destroy() {
		if(pool != null) {
			pool.destroy();
		}
	}


	public Jedis getInstance() {
		if (pool == null) {
			createJedisPool();
		}

		return pool.getResource();
	}
	
	private void createJedisPool() {
		pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
	}

	private JedisPool pool;
}

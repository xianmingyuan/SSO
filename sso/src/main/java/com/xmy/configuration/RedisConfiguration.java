package com.xmy.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
	
	private final static transient Logger log = LoggerFactory.getLogger(RedisConfiguration.class);

	@Bean(name = "jedis.pool")
	@Autowired
	public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config, @Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") int port) {
		log.debug("生成jedisPool...");
		return new JedisPool(config, host, port);
	}
	
	@Bean(name= "jedis.pool.config")  
    public JedisPoolConfig jedisPoolConfig () {  
		log.debug("生成JedisPoolConfig...");
        JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxTotal(100);  
        config.setMaxIdle(10);  
        config.setMaxWaitMillis(100000);  
        return config;  
    }  

}

package com.example.demo.webConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisConfig {
	
	@Value("${spring.redis.host}")
	private String hostName;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(new RedisStandaloneConfiguration(hostName, port));
		return lettuceConnectionFactory;
	}
}

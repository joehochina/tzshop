package com.tengzhuo.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.tools.internal.xjc.model.SymbolSpace;

public class JedisClientTest {

	@Test
	public void testJedisClient() throws Exception{
		//初始化spring容器
		ApplicationContext applicationContext= new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("springredis", "springredis");
		String string = jedisClient.get("springredis");
	
		System.out.println(string);
	}
}

package com.tengzhuo.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * jedis操作redis缓存测试
 * <p>Title: JedisTest.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月23日</p>
 * <p>version:1.0</p>
 */
public class JedisTest {
	
	/**
	 * 测试jedis单机版
	 * @throws Exception
	 */
	@Test
	public void testJedis() throws Exception{
		
		//创建一个链接Jedis对象，参数Host port
		Jedis jedis = new Jedis("192.168.1.107", 6379);
		//直接使用jedis操作redis 
		jedis.set("test123", "my first jedis");
		String string = jedis.get("test123");
		System.out.println(string);
		jedis.close();
	}
	
	/**
	 * 使用jedis连接池操作redis缓存
	 * @throws Exception
	 */
	@Test
	public void testJedisPool() throws Exception{
		//创建一个连接池对象
		JedisPool jedisPool = new JedisPool("192.168.1.107", 6379);
		//从连接池获取一个连接
		Jedis jedis = jedisPool.getResource();
		
		//使用jedis操作redis		
		jedis.set("test456", "my first jedis pool test");
		String string = jedis.get("test456");
		System.out.println(string);

		//关闭连接，每次使用后都要关闭，进入资源回收
		jedis.close();
		//关闭连接池
		jedisPool.close();
	}
	
	/**
	 * 使用redis集群操作缓存
	 * @throws Exception
	 */
	@Test
	public void testJedisCluster() throws Exception{
		//创建Jedis cluster对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.1.107", 7001));
		nodes.add(new HostAndPort("192.168.1.107", 7002));
		nodes.add(new HostAndPort("192.168.1.107", 7003));
		nodes.add(new HostAndPort("192.168.1.107", 7004));
		nodes.add(new HostAndPort("192.168.1.107", 7005));
		nodes.add(new HostAndPort("192.168.1.107", 7006));
			
		JedisCluster jedisCluster = new JedisCluster(nodes);
		
		//向redis集群添加数据
		jedisCluster.set("redisCluster01", "redisCluster01");
		String string = jedisCluster.get("redisCluster01");
		System.out.println(string);
		
		//关闭连接
		jedisCluster.close();
	
	}

}

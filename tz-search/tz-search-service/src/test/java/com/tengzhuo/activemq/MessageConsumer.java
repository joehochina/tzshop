package com.tengzhuo.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 接收消息 测试 
 * <p>Title: MessageConsumer.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月27日</p>
 * <p>version:1.0</p>
 */
public class MessageConsumer {

	@Test
	public void testMsgConsumer() throws Exception{
		//初始化 spring容器 
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		System.in.read();
	}
}

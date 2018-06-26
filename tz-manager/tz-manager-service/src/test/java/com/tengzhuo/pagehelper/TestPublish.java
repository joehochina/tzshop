package com.tengzhuo.pagehelper;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPublish {
	
	@Test
	public void testPublishService() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
		System.out.println("start service");
		System.in.read();
		System.out.println("stop service");
	
	}

}

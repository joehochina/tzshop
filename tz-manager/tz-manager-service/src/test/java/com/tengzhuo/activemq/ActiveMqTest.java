package com.tengzhuo.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
/**
 * MQ消息通信的两种方式 
 * <p>Title: ActiveMqTest.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月26日</p>
 * <p>version:1.0</p>
 */
public class ActiveMqTest {

	@Test
	public void testQueueProducer() throws Exception{
	//	创建一个连接工厂对象，需要指定服务的ip及端口
		String brokerURL="tcp://192.168.1.106:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
	//	使用工厂对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
	//	开启连接，调用Connection对象的start方法
		connection.start();
	//	创建一个 Session对象,第一个参数，是否开启事务，如果为true则第二个参数无效
	//  第二个参数是应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	//	使用 session对象创建一个Destination对象，两种形式的queue,topic
		Queue queue = session.createQueue("test-queue");
	//	使用session对象创建一个 Producer对象 
		MessageProducer producer = session.createProducer(queue);
	//	创建 一个Message对象 ，可以使用 TestMessage
//	    TextMessage message = new ActiveMQTextMessage();
//		message.setText("hello ActiveMQ");		
		TextMessage message = session.createTextMessage("hello ActiveMQ");		
	//	发送消息
		producer.send(message);
	//	关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testQueueConsumer() throws Exception{
//		创建一个连接工厂对象，需要指定服务的ip及端口
			String brokerURL="tcp://192.168.1.106:61616";
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		//	使用工厂对象创建一个Connection对象
			Connection connection = connectionFactory.createConnection();
		//	开启连接，调用Connection对象的start方法
			connection.start();
		//	创建一个 Session对象,第一个参数，是否开启事务，如果为true则第二个参数无效
		//  第二个参数是应答模式
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//	使用 session对象创建一个Destination对象，两种形式的queue,topic
			Queue queue = session.createQueue("test-queue");
		//	使用session对象创建一个 consumer对象 
			MessageConsumer consumer = session.createConsumer(queue);
			//	接收消息
			consumer.setMessageListener(new MessageListener() {				
				@Override
				public void onMessage(Message message) {
					// 打印结果
					TextMessage textMessage = (TextMessage) message;					
					try {
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			});			
		//等待消息
			System.in.read();			
		//	关闭资源
			consumer.close();
			session.close();
			connection.close();
	}
	
	@Test
	public void testTopicProducer() throws Exception{
	//	创建一个连接工厂对象，需要指定服务的ip及端口
		String brokerURL="tcp://192.168.1.106:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
	//	使用工厂对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
	//	开启连接，调用Connection对象的start方法
		connection.start();
	//	创建一个 Session对象,第一个参数，是否开启事务，如果为true则第二个参数无效
	//  第二个参数是应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	//	使用 session对象创建一个Destination对象，两种形式的queue,topic
		Topic topic = session.createTopic("test-topic");
	//	Queue queue = session.createQueue("test-queue");
	//	使用session对象创建一个 Producer对象 
		MessageProducer producer = session.createProducer(topic);
	//	创建 一个Message对象 ，可以使用 TestMessage
//	    TextMessage message = new ActiveMQTextMessage();
//		message.setText("hello ActiveMQ");		
		TextMessage message = session.createTextMessage("hello ActiveMQ topic");		
	//	发送消息
		producer.send(message);
	//	关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testTopicConsumer() throws Exception{
//		创建一个连接工厂对象，需要指定服务的ip及端口
			String brokerURL="tcp://192.168.1.106:61616";
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		//	使用工厂对象创建一个Connection对象
			Connection connection = connectionFactory.createConnection();
		//	开启连接，调用Connection对象的start方法
			connection.start();
		//	创建一个 Session对象,第一个参数，是否开启事务，如果为true则第二个参数无效
		//  第二个参数是应答模式
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//	使用 session对象创建一个Destination对象，两种形式的queue,topic
			Topic topic = session.createTopic("test-topic");
		//	使用session对象创建一个 consumer对象 
			MessageConsumer consumer = session.createConsumer(topic);
			//	接收消息
			consumer.setMessageListener(new MessageListener() {				
				@Override
				public void onMessage(Message message) {					// 打印结果
					TextMessage textMessage = (TextMessage) message;					
					try {
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});			
		//等待消息
			System.in.read();			
		//	关闭资源
			consumer.close();
			session.close();
			connection.close();
	}
	
}

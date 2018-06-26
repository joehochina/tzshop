package com.tengzhuo.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMqSpringTest {
	@Test
	public void testMessage(){
		//初始化 spring容器 
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//从容器中 获得JmsTemplate对象
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		//从容器中获得一个Destination对象
		Destination destination=(Destination) context.getBean("queueDestination");
		//发送消息
		jmsTemplate.send(destination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage("send ativemq message from spring");
			}
		});	
	
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
			Queue queue = session.createQueue("spring-queue");
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
	

}

package de.fhkoeln.eis.radioexpert.messaging;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class Client {
	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:62626");
		Connection connection = connectionFactory.createConnection();

		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		connection.start();
		Topic topic = new ActiveMQTopic("director");

		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				System.out.println("Auch hier angekommen");

			}
		});
		MessageProducer messageProducer = session.createProducer(topic);

		TextMessage textMessage = session.createTextMessage();
		textMessage.setText("from command line client : " + new Date());
		messageProducer.send(textMessage);

		System.out.println("Eine Nachricht von Commandlinetool geschickt");

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.stop();
		session.close();
		connection.close();
	}
}
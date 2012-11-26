package de.fhkoeln.eis.radioexpert.proofofconcept;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;

public class Client {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:62626");

        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        connection.start();


        Queue queue = session.createQueue("persistenceRequest");
        Queue queue2 = session.createQueue("persistenceResponse");

        MessageConsumer consumer = session.createConsumer(queue2);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("acuh hier angekommen ");
            }
        });

        MessageProducer messageProducer = session.createProducer(queue);

        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("from command line client : " + new Date());
        messageProducer.send(textMessage);

        System.out.println("Eine Nachricht von Commandlinetool geschickt");

        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        connection.stop();
        session.close();
        connection.close();
    }
}
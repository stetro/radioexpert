package de.fhkoeln.eis.radioexpert.messaging;

import java.text.DateFormat;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoSender {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Transactional
	public void sendIt() {

		jmsTemplate.send(new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
				textMessage.setText(DateFormat.getDateTimeInstance().format(
						new Date()));
				return textMessage;
			}
		});
	}

	public void sentItPeriodly() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					sendIt();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).run();
	}
}
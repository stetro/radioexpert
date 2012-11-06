package de.fhkoeln.eis.radioexpert.server.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.stereotype.Service;

/**
 * Datenbank Listener hat alle Topics Abboniert und speichert die Nachrichten in
 * die Datenbank ab.
 * 
 * @author Steffen Troester
 */
@Service
public class DatabaseListener implements javax.jms.MessageListener {
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				System.out.println("Write to Database: "
						+ ((TextMessage) message).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
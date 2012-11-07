package de.fhkoeln.eis.radioexpert.server.services;

import de.fhkoeln.eis.radioexpert.messaging.messages.TwitterMessage;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

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
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            if (objectMessage.getObject() instanceof TwitterMessage) {
                System.out.println("Write to Database: " + objectMessage.getObject().toString());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
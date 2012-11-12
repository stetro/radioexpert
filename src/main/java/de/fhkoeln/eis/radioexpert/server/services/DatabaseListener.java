package de.fhkoeln.eis.radioexpert.server.services;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseListener.class);

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Session s = sessionFactory.openSession();
            s.save(objectMessage.getObject());
        } catch (JMSException e) {
            logger.error("Fehler beim Speichern in die Datenbank ! " + e.getMessage());
        }
    }
}
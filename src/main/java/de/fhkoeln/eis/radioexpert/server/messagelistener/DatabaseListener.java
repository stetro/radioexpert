package de.fhkoeln.eis.radioexpert.server.messagelistener;

import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastMessage;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Datenbank Listener hat alle Topics Abboniert und speichert die Nachrichten in
 * die Datenbank ab.
 *
 * @author Steffen Troester
 */
@Service
public class DatabaseListener implements MessageListener {

    @Autowired
    SessionFactory sessionFactory;

    public static BroadcastMessage currentBroadcast;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseListener.class);

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            logger.info("Message empfangen !");
            if (objectMessage instanceof BroadcastMessage) {
                safeCurrentBroadcastId((BroadcastMessage) objectMessage);
            }
            Session s = sessionFactory.openSession();
            s.save(objectMessage.getObject());
            s.close();
        } catch (ClassCastException e) {
            logger.error("Unbekannte Nachricht wurde verschickt !" + e.getMessage());
        } catch (Exception e) {
            logger.error("Fehler beim Speichern in die Datenbank ! " + e.getMessage());
        }
    }

    private void safeCurrentBroadcastId(BroadcastMessage objectMessage) {
        currentBroadcast = objectMessage;
    }
}
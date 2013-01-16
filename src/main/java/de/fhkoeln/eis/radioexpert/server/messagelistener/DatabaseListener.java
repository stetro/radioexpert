package de.fhkoeln.eis.radioexpert.server.messagelistener;

import de.fhkoeln.eis.radioexpert.messaging.messages.*;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * Datenbank Listener hat alle Topics Abboniert und speichert die Nachrichten in
 * die Datenbank ab. Zusätzlich verknüpft der Listener alle eingehenden Nachrichten
 * mit der aktuellen Sendung.
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
            Serializable object = objectMessage.getObject();
            manageMessages(object);
            Session s = sessionFactory.openSession();
            s.save(object);
            s.close();
        } catch (ClassCastException e) {
            logger.error("Unbekannte Nachricht wurde verschickt !" + e.getMessage());
        } catch (Exception e) {
            logger.error("Fehler beim Speichern in die Datenbank ! " + e.getMessage());
        }
    }

    /**
     * Ersetzt die Letzte Sendung oder setzt die neue Zeit in die Zugehörigkeit der Sendung
     *
     * @param object
     */
    private void manageMessages(Serializable object) {

        //TODO: Wenn currentBroadcast == null muss eine instanz aus der Datenbank geladen werden !

        if (object instanceof BroadcastMessage) {
            currentBroadcast = (BroadcastMessage) object;
            logger.info("Neue Sendung wurde erzeugt !");
        } else if (currentBroadcast == null) {
            logger.error("keine Sendung wurde erzeugt !");
        }

        // Referenzierung erzeugen
        if (object instanceof ChatMessage && currentBroadcast != null) {
            ChatMessage chatMessage = (ChatMessage) object;
            chatMessage.setBroadcastCreatedAt(currentBroadcast.getCreatedAt());
        }
        if (object instanceof TwitterMessage && currentBroadcast != null) {
            TwitterMessage twitterMessage = (TwitterMessage) object;
            twitterMessage.setBroadcastCreatedAt(currentBroadcast.getCreatedAt());
        }
        if (object instanceof FacebookMessage && currentBroadcast != null) {
            FacebookMessage facebookMessage = (FacebookMessage) object;
            facebookMessage.setBroadcastCreatedAt(currentBroadcast.getCreatedAt());
        }
        if (object instanceof MailMessage && currentBroadcast != null) {
            MailMessage mailMessage = (MailMessage) object;
            mailMessage.setBroadcastCreatedAt(currentBroadcast.getCreatedAt());
        }
    }


}
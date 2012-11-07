package de.fhkoeln.eis.radioexpert.server.services;

import de.fhkoeln.eis.radioexpert.messaging.messages.TwitterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Datenbank Listener hat alle Topics Abboniert und speichert die Nachrichten in
 * die Datenbank ab.
 *
 * @author Steffen Troester
 */
@Service
public class DatabaseListener implements javax.jms.MessageListener {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseListener.class);

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            if (objectMessage.getObject() instanceof TwitterMessage) {
                TwitterMessage twitterMessage = (TwitterMessage) objectMessage.getObject();
                jdbcTemplate.update("INSERT INTO TWEETS VALUES(?,?,?)", twitterMessage.getMessage(), twitterMessage.getUser(), twitterMessage.getTime());
                logger.info("Tweet wurde abgespeichert ! - " + twitterMessage.getMessage());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
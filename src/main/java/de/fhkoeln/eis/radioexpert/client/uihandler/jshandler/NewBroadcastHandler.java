package de.fhkoeln.eis.radioexpert.client.uihandler.jshandler;

import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Handler fuer JavaScript Komponente
 * <p/>
 * User: Steffen Tröster
 * Date: 16.12.12
 * Time: 13:33
 */
@Service
public class NewBroadcastHandler {
    private static JmsTemplate jmsTemplate;
    public static BroadcastMessage currentBroadcastMessage;
    private Logger logger = LoggerFactory.getLogger(NewBroadcastHandler.class);

    @Autowired
    public NewBroadcastHandler(JmsTemplate jmsTemplate) {
        NewBroadcastHandler.jmsTemplate = jmsTemplate;
        jmsTemplate.setPubSubDomain(true);
    }

    public NewBroadcastHandler() {
    }

    public void newBroadcast(long givenFrom, long givenTo, String title, String intro, String description) {
        logger.info("Sendung wird erzeugt ...");
        Date from = new Date(givenFrom);
        Date to = new Date(givenTo);
        BroadcastMessage broadcastMessage = new BroadcastMessage(from, to, title, intro, description);
        jmsTemplate.convertAndSend("broadcast", broadcastMessage);
    }
}

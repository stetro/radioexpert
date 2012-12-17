package de.fhkoeln.eis.radioexpert.client.uihandler.jshandler;

import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.ChatMessage;
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

    @Autowired
    public NewBroadcastHandler(JmsTemplate jmsTemplate) {
        NewBroadcastHandler.jmsTemplate = jmsTemplate;
        jmsTemplate.setPubSubDomain(true);
    }

    public NewBroadcastHandler() {
    }

    public void newBroadcast(int givenFrom, int givenTo, String title, String intro, String description) {
        Date from = new Date(givenFrom);
        Date to = new Date(givenTo);
        BroadcastMessage broadcastMessage = new BroadcastMessage(from, to, title, intro, description);
        jmsTemplate.convertAndSend("broadcast", broadcastMessage);
    }
}

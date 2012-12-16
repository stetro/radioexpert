package de.fhkoeln.eis.radioexpert.client.uihandler.jshandler;

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

    public void newBroadcast() {
        jmsTemplate.convertAndSend("chat", new ChatMessage("Ich", "Text", new Date()));
    }
}

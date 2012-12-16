package de.fhkoeln.eis.radioexpert.client.util;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * Laedt die aktuellste Sendung nach und uebergibt diese Informationen dem Fenser
 * User: Steffen Tröster
 * Date: 26.11.12
 * Time: 09:28
 */
public class BroadcastLoader implements EventHandler<ActionEvent> {

    private static JmsTemplate jmsTemplate;

    public static Logger logger = LoggerFactory.getLogger(BroadcastLoader.class);

    @Override
    public void handle(ActionEvent actionEvent) {
        jmsTemplate = ClientApplication.context.getBean(JmsTemplate.class);
        jmsTemplate.setPubSubDomain(false);
        sendBroadcastRequest();
    }

    /**
     * Senden der Anfrage
     */
    private void sendBroadcastRequest() {
        jmsTemplate.convertAndSend("persistenceRequest", new BroadcastRequest());
        logger.info("Anfrage geschickt !");
    }
}

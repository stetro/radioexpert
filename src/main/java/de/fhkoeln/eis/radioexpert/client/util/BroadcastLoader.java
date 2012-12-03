package de.fhkoeln.eis.radioexpert.client.util;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

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
        try {
            listenOnBroadcastResponse();
        } catch (JMSException e) {
            logger.error("Fehler beim Empfangen der Nachricht!");
        }
        sendBroadcastRequest();
    }

    /**
     * Baut Verbindung mit JMS Middleware auf und bindet Callback fuer die PersistenceResponse
     *
     * @throws JMSException
     */
    private void listenOnBroadcastResponse() throws JMSException {
        final Connection connection = jmsTemplate.getConnectionFactory().createConnection();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();

        Queue queue = session.createQueue("persistenceResponse");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                logger.info("Response Empfangen !");
                jmsTemplate.setPubSubDomain(true);
                try {
                    connection.stop();
                    session.close();
                    connection.close();
                } catch (JMSException e) {
                    logger.error("Verbindung konnte nicht geschlossen werden !");
                }
            }
        });
    }

    /**
     * Senden der Anfrage
     */
    private void sendBroadcastRequest() {
        jmsTemplate.convertAndSend("persistenceRequest", new BroadcastRequest());
        logger.info("Anfrage geschickt !");
    }
}

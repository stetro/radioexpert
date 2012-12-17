package de.fhkoeln.eis.radioexpert.server.messagelistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * DatabaseController ist ein Service der auf die MessageQueue persistence.request horcht und
 * die Antwort auf persistence.response schickt.
 * User: Steffen Tröster
 * Date: 21.11.12
 * Time: 14:17
 */
@Service
public class DatabaseController implements MessageListener{
    @Autowired
    private JmsTemplate jmsTemplate;

    private static Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    @Override
    public void onMessage(Message message) {
        jmsTemplate.convertAndSend("persistenceResponse","FooBar");
        logger.info("Abfragenachricht wurde empfangen  !!");
    }
}
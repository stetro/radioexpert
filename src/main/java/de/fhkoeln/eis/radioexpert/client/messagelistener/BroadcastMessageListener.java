package de.fhkoeln.eis.radioexpert.client.messagelistener;


import de.fhkoeln.eis.radioexpert.client.uihandler.TimeLineHandler;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Empfängt neu erstellte Sendungen
 * <p/>
 * User: Steffen Tröster
 * Date: 17.12.12
 * Time: 11:03
 */
@Service
public class BroadcastMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(BroadcastMessageListener.class);

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            BroadcastMessage broadcastMessage = (BroadcastMessage) objectMessage.getObject();
            logger.info("Broadcastmessage Erhalten + " + broadcastMessage.getTitle());
            TimeLineHandler.setBroadcast(broadcastMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

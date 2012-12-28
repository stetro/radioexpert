package de.fhkoeln.eis.radioexpert.client.messagelistener;

import de.fhkoeln.eis.radioexpert.client.uihandler.ChatHandler;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastResponse;
import de.fhkoeln.eis.radioexpert.messaging.messages.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Empfaengt den Response vom DatabaseController der ServerInstance
 * <p/>
 * User: Steffen Tröster
 * Date: 16.12.12
 * Time: 12:38
 */
@Service
public class LoadBroadcastMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(LoadBroadcastMessageListener.class);

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            BroadcastResponse response = (BroadcastResponse) objectMessage.getObject();
            for (ChatMessage c : response.getChatMessages()) {
                ChatHandler.displayChatMessage(c);
            }
            logger.info("Response erhalten !!");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

package de.fhkoeln.eis.radioexpert.client.messagelistener;

import de.fhkoeln.eis.radioexpert.client.uihandler.ChatHandler;
import de.fhkoeln.eis.radioexpert.messaging.messages.ChatMessage;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Message Listener für den ChatHandler (definiert im applicationContext)
 * <p/>
 * User: Steffen Tröster
 * Date: 14.12.12
 * Time: 12:02
 */
@Service
public class ChatHandlerMessageListener implements MessageListener {

    public ChatHandlerMessageListener() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            ChatMessage chatMessage = (ChatMessage) ((ObjectMessage) message).getObject();
            ChatHandler.displayChatMessage(chatMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

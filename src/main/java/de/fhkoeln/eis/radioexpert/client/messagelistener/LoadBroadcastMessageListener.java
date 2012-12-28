package de.fhkoeln.eis.radioexpert.client.messagelistener;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.client.uihandler.ChatHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.SocialMediaListHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.TimeLineHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.jshandler.NewElementHandler;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastResponse;
import de.fhkoeln.eis.radioexpert.messaging.messages.ChatMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.SocialMediaMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.TimeLineElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

/**
 * Empfaengt den Response vom DatabaseController der ServerInstance
 * <p/>
 * User: Steffen Tröster
 * Date: 16.12.12
 * Time: 12:38
 */

public class LoadBroadcastMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(LoadBroadcastMessageListener.class);



    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            BroadcastResponse response = (BroadcastResponse) objectMessage.getObject();
            ChatHandler.clearMessages();
            for (ChatMessage c : response.getChatMessages()) {
                ChatHandler.displayChatMessage(c);
            }
            SocialMediaListHandler.clearMessages();
            for (SocialMediaMessage m : response.getSocialMediaMessages()) {
                SocialMediaListHandler.displayMessage(m);
            }
            TimeLineHandler.clearElements();
            for (TimeLineElement e : response.getTimeLineElements()) {
                TimeLineHandler.updateElement(e);
            }
            NewElementHandler.currentBroadcastMessage = response.getBroadcastMessage();

            TimeLineHandler.setBroadcast(response.getBroadcastMessage());
            logger.info("Response erhalten !!");
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}

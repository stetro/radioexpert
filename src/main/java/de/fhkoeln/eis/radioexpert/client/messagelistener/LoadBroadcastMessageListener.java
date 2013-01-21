package de.fhkoeln.eis.radioexpert.client.messagelistener;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.client.uihandler.ChatHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.SocialMediaListHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.TimeLineHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.jshandler.NewElementHandler;
import de.fhkoeln.eis.radioexpert.client.util.InfoBox;
import de.fhkoeln.eis.radioexpert.client.util.UserRole;
import de.fhkoeln.eis.radioexpert.messaging.SocialMediaMessage;
import de.fhkoeln.eis.radioexpert.messaging.TimeLineElement;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastResponse;
import de.fhkoeln.eis.radioexpert.messaging.messages.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Empfaengt den Response vom DatabaseController der ServerInstance
 * <p/>
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
            if (response.getBroadcastMessage() == null) {
                InfoBox.showMessage("Es muss zunächst eine Sendung erstellt werden!");
                return;
            }

            TimeLineHandler.clearElements();
            TimeLineHandler.setBroadcast(response.getBroadcastMessage());
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
            if (ClientApplication.role == UserRole.REDAKTEUR) {
                TimeLineHandler.addElementDropDownMenuButton.setVisible(true);
            }
            logger.info("Response erhalten !!");
        } catch (Exception e) {
            InfoBox.showMessage("Fehler beim Laden der Nachrichten!");
        }
    }
}

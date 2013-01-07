package de.fhkoeln.eis.radioexpert.client.messagelistener;

import de.fhkoeln.eis.radioexpert.client.uihandler.SocialMediaListHandler;
import de.fhkoeln.eis.radioexpert.messaging.SocialMediaMessage;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * Uebernimmt das Empfangen der SocialMedia Messages und stellt diese im Handler dar
 * (definiert im applicationContext)
 * <p/>
 * User: Steffen Tröster
 * Date: 14.12.12
 * Time: 12:05
 */
@Service
public class SocialMediaListHandlerMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            Serializable objectMessage = ((ObjectMessage) message).getObject();
            SocialMediaMessage socialMediaMessage = (SocialMediaMessage) objectMessage;
            SocialMediaListHandler.displayMessage(socialMediaMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

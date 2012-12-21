package de.fhkoeln.eis.radioexpert.client.messagelistener;

import de.fhkoeln.eis.radioexpert.client.uihandler.TimeLineHandler;
import de.fhkoeln.eis.radioexpert.messaging.messages.AudioMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.InterviewMessage;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Empfängt Blöcke wie "Mederation" und "Songs" ect.
 * <p/>
 * User: Steffen Tröster
 * Date: 18.12.12
 * Time: 09:51
 */
@Service
public class TimeLineMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            if (objectMessage.getObject() instanceof InterviewMessage) {
                TimeLineHandler.updateElement((InterviewMessage) objectMessage.getObject());
            } else if (objectMessage.getObject() instanceof AudioMessage) {
                TimeLineHandler.updateElement((AudioMessage) objectMessage.getObject());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

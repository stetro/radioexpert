package de.fhkoeln.eis.radioexpert.client.messagelistener;

import de.fhkoeln.eis.radioexpert.client.uihandler.TimeLineHandler;
import de.fhkoeln.eis.radioexpert.messaging.messages.InterviewElementMessage;
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
public class TimeLineElementMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage)message;
        try {
            if(objectMessage.getObject() instanceof InterviewElementMessage)
            {
                TimeLineHandler.updateElement((InterviewElementMessage)objectMessage.getObject());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
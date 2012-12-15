package de.fhkoeln.eis.radioexpert.client.messagelistener;


import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.client.uihandler.OnlineStatusHandler;
import de.fhkoeln.eis.radioexpert.messaging.messages.OnlineStatusMessage;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * Message Listener fuer die ChatHandler Komponente
 * Sendet alle n Sekunden einen Onlinestatus und uebergibt username
 * und timestamp um die Online stehenden Nutzer anzeigen zu koennen
 * <p/>
 * User: Steffen Tröster
 * Date: 15.12.12
 * Time: 10:09
 */
@Service
public class OnlineStatusHandlerMessageListener implements MessageListener {

    private static final int seconds = 5;
    private Logger logger = LoggerFactory.getLogger(OnlineStatusHandlerMessageListener.class);

    @Autowired
    public OnlineStatusHandlerMessageListener(final JmsTemplate jmsTemplate) {
        // Thread fuer Erneuten Online Status veroeffentlichung
        Runnable RefreshOnlineStatusThread = new Runnable() {
            @Override
            public void run() {
                while (Platform.isImplicitExit()) {
                    try {
                        Thread.sleep(seconds * 1000);
                        OnlineStatusMessage m = new OnlineStatusMessage(ClientApplication.user, ClientApplication.role);
                        jmsTemplate.convertAndSend("onlinestatus", m);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(RefreshOnlineStatusThread).start();
    }

    @Override
    public void onMessage(Message message) {
        try {
            Serializable s = ((ObjectMessage) message).getObject();
            OnlineStatusMessage statusMessage = (OnlineStatusMessage) s;
            OnlineStatusHandler.signPersonAsOnline(statusMessage);
            OnlineStatusHandler.updateOnlineStatus();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

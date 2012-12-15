package de.fhkoeln.eis.radioexpert.client.uihandler;


import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

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


    private JmsTemplate jmsTemplate;

    private Logger logger = LoggerFactory.getLogger(OnlineStatusHandlerMessageListener.class);


    @Autowired
    public OnlineStatusHandlerMessageListener(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;

        new Thread(new Runnable() {
            @Override
            public void run() {
                int seconds = 10;
                while (Platform.isImplicitExit()) {
                    logger.info("Online Status uebermittelt ...");
                    OnlineStatusHandlerMessageListener.this.jmsTemplate.convertAndSend("onlinestatus", ClientApplication.user);
                    try {
                        Thread.sleep(seconds * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            OnlineStatusHandler.signPersonAsOnline(tm.getText());
            OnlineStatusHandler.updateOnlineStatus();
        } catch (JMSException e) {
            e.printStackTrace();

        }
    }
}

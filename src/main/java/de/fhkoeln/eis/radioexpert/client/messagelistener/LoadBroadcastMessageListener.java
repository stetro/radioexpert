package de.fhkoeln.eis.radioexpert.client.messagelistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Empfaengt den Response vom DatabaseController der ServerInstance
 * <p/>
 * User: Steffen Tröster
 * Date: 16.12.12
 * Time: 12:38
 */
// TODO: ALternative überlegen !! So geht das nicht !
@Service
public class LoadBroadcastMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(LoadBroadcastMessageListener.class);

    @Override
    public void onMessage(Message message) {
        logger.info("Response erhalten !!");
    }
}

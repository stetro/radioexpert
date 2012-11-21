package de.fhkoeln.eis.radioexpert.server.services;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import de.fhkoeln.eis.radioexpert.messaging.messages.FacebookMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Facebook Service soll aktuelle Facebook Chronik Eintraege in das System speisen.
 * <p/>
 * User: Steffen Tröster
 * Date: 12.11.12
 * Time: 11:04
 */

@Service
public class FacebookService implements RadioExpertService {

    /**
     * {@link org.springframework.jms.core.JmsTemplate} aus der applicationServerContext.xml (component-scan)
     */
    @Autowired
    JmsTemplate jmsTemplate;

    /**
     * FacebookService logger
     */
    private static Logger logger = LoggerFactory.getLogger(FacebookService.class);

    /**
     * PollInteravl in ms angegeben
     */
    private int pollInterval = 20 * 1000;

    @Override
    public void start() {
        logger.info("Facebook Service startet ...");
        // Thread starten
        new Thread(new Runnable() {
            @Override
            public void run() {
                jmsTemplate.setPubSubDomain(true);
                FacebookClient facebookClient = new DefaultFacebookClient("");
                // FacebookClient.AccessToken accessToken = new FacebookClient.AccessToken();
                // FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken());
                while (true) {
                    try {
                        JsonObject jo = facebookClient.fetchObject("domradio.de/feed", JsonObject.class);
                        FacebookMessage facebookMessage = new FacebookMessage();

                        logger.info(jo.getJsonArray("data").getJsonObject(0).get("message").toString());

                        facebookMessage.setFacebookId(jo.getJsonArray("data").getJsonObject(0).get("id").toString());
                        facebookMessage.setMessage(jo.getJsonArray("data").getJsonObject(0).get("message").toString());

                        jmsTemplate.convertAndSend("director", facebookMessage);
                        Thread.sleep(pollInterval);
                    } catch (InterruptedException e) {
                        logger.info("Fehler beim Facebook Service ! ...");
                        break;
                    }
                }
            }
        }).start();
    }
}

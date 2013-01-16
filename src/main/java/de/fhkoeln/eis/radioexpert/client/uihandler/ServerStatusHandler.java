package de.fhkoeln.eis.radioexpert.client.uihandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Prueft ob die Serververbindung funktionstuechtig ist
 * <p/>
 * Date: 15.12.12
 * Time: 16:10
 */
@Service
public class ServerStatusHandler {

    private static JmsTemplate jmsTemplate;

    @Autowired
    public ServerStatusHandler(JmsTemplate jmsTemplate) {
        ServerStatusHandler.jmsTemplate = jmsTemplate;
    }

    /**
     * Testet die Serververbindung mit einer Testnachricht. Exception Kontollfluss
     * ist hier erlaubt, da keine bekannte andere Möglichkeit gefunden!
     *
     * @return serverStatus
     */
    public static boolean serverIsAvailable() {
        try {
            jmsTemplate.setPubSubDomain(true);
            jmsTemplate.convertAndSend("serverstatuscheck", "Test");
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

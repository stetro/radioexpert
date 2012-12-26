package de.fhkoeln.eis.radioexpert.client.uihandler.jshandler;

import de.fhkoeln.eis.radioexpert.client.uihandler.MoreInformationHandler;
import de.fhkoeln.eis.radioexpert.messaging.messages.AudioMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Handler fuer JavaScript Komponente
 * <p/>
 * User: Steffen Tröster
 * Date: 16.12.12
 * Time: 13:33
 */
@Service
public class NewElementHandler {
    private static JmsTemplate jmsTemplate;
    public static BroadcastMessage currentBroadcastMessage;
    private Logger logger = LoggerFactory.getLogger(NewElementHandler.class);

    @Autowired
    public NewElementHandler(JmsTemplate jmsTemplate) {
        NewElementHandler.jmsTemplate = jmsTemplate;
        jmsTemplate.setPubSubDomain(true);
    }

    public NewElementHandler() {
    }

    public void newAudio(String title, long givenFrom, long givenTo) {
        logger.info("Audio wird erzeugt ...");

        // Korrektes Datum einsetzen
        Calendar cFrom = Calendar.getInstance();
        Calendar cTo = Calendar.getInstance();
        getGivenTimes(givenFrom, givenTo, cFrom, cTo);

        // Nachricht senden
        AudioMessage audioMessage = new AudioMessage(title, cFrom.getTime(), cTo.getTime());
        audioMessage.setCreatedAt(new Date());
        jmsTemplate.convertAndSend("audio", audioMessage);
    }

    public void updateAudio(String title, long givenFrom, long givenTo) {
        logger.info("Audio wird aktualiert ...");
        // Korrektes Datum einsetzen
        Calendar cFrom = Calendar.getInstance();
        Calendar cTo = Calendar.getInstance();
        getGivenTimes(givenFrom, givenTo, cFrom, cTo);

        // Nachricht senden
        AudioMessage audioMessage = (AudioMessage) MoreInformationHandler.currentSelectedElement;
        audioMessage.setTitle(title);
        audioMessage.setStart(cFrom.getTime());
        audioMessage.setEnd(cTo.getTime());
        jmsTemplate.convertAndSend("audio", audioMessage);
    }

    public void newBroadcast(long givenFrom, long givenTo, String title, String intro, String description) {
        logger.info("Sendung wird erzeugt ...");
        Date from = new Date(givenFrom);
        Date to = new Date(givenTo);
        BroadcastMessage broadcastMessage = new BroadcastMessage(from, to, title, intro, description);
        jmsTemplate.convertAndSend("broadcast", broadcastMessage);
    }

    private void getGivenTimes(long givenFrom, long givenTo, Calendar cFrom, Calendar cTo) {
        cFrom.setTime(new Date(givenFrom));
        cTo.setTime(new Date(givenTo));
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(currentBroadcastMessage.getStart());
        cFrom.set(cStart.get(Calendar.YEAR), cStart.get(Calendar.MONTH), cStart.get(Calendar.DATE));
        cTo.set(cStart.get(Calendar.YEAR), cStart.get(Calendar.MONTH), cStart.get(Calendar.DATE));
    }
}

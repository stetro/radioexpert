package de.fhkoeln.eis.radioexpert.client.uihandler.jshandler;

import de.fhkoeln.eis.radioexpert.client.uihandler.MoreInformationHandler;
import de.fhkoeln.eis.radioexpert.client.util.InfoBox;
import de.fhkoeln.eis.radioexpert.messaging.messages.AudioMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.InterviewMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Handler fuer JavaScript Komponente bei neuen Elementen aus Formularen
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

    /**
     * Erstellt ein neues Interview mit folgenden Eigenschaften (aus JavaScript)
     *
     * @param thema
     * @param start
     * @param end
     * @param name
     * @param phone
     * @param mail
     * @param street
     * @param city
     * @param questions
     * @param infotext
     */
    public void newInterview(String thema, long start, long end, String name, String phone, String mail, String street, String city, String questions, String infotext) {
        logger.info("Interview wird erzeugt ...");
        Calendar cFrom = Calendar.getInstance();
        Calendar cTo = Calendar.getInstance();
        getGivenTimes(start, end, cFrom, cTo);

        InterviewMessage interviewMessage = new InterviewMessage();
        interviewMessage.setTitle(thema);
        interviewMessage.setStart(cFrom.getTime());
        interviewMessage.setEnd(cTo.getTime());
        interviewMessage.setName(name);
        interviewMessage.setPhone(phone);
        interviewMessage.setMail(mail);
        interviewMessage.setStreet(street);
        interviewMessage.setCity(city);
        interviewMessage.setQuestions(questions);
        interviewMessage.setInfo(infotext);
        interviewMessage.setBroadcastCreatedAt(currentBroadcastMessage.getCreatedAt());

        jmsTemplate.convertAndSend("interview", interviewMessage);
        InfoBox.showMessage("Interview wurde erfolgreich erstellt !");
        MoreInformationHandler.clear();
    }

    /**
     * Aktualisiert ein neues Interview mit folgenden Eigenschaften (aus JavaScript)
     *
     * @param thema
     * @param start
     * @param end
     * @param name
     * @param phone
     * @param mail
     * @param street
     * @param city
     * @param questions
     * @param infotext
     */
    public void updateInterview(String thema, long start, long end, String name, String phone, String mail, String street, String city, String questions, String infotext) {
        logger.info("Interview wird erzeugt ...");
        Calendar cFrom = Calendar.getInstance();
        Calendar cTo = Calendar.getInstance();
        getGivenTimes(start, end, cFrom, cTo);

        InterviewMessage interviewMessage = (InterviewMessage) MoreInformationHandler.currentSelectedElement;
        interviewMessage.setTitle(thema);
        interviewMessage.setStart(cFrom.getTime());
        interviewMessage.setEnd(cTo.getTime());
        interviewMessage.setName(name);
        interviewMessage.setPhone(phone);
        interviewMessage.setMail(mail);
        interviewMessage.setStreet(street);
        interviewMessage.setCity(city);
        interviewMessage.setQuestions(questions);
        interviewMessage.setInfo(infotext);
        interviewMessage.setBroadcastCreatedAt(currentBroadcastMessage.getCreatedAt());

        jmsTemplate.convertAndSend("interview", interviewMessage);
        InfoBox.showMessage("Interview wurde erfolgreich bearbeitet !");
        MoreInformationHandler.clear();
    }

    /**
     * Erstellt ein neues Audio mit folgenden Eigenschaften (aus JavaScript)
     *
     * @param title
     * @param givenFrom
     * @param givenTo
     */
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
        InfoBox.showMessage("Audio wurde erfolgreich erstellt !");
        MoreInformationHandler.clear();
    }

    /**
     * Aktualisiert ein neues Audio mit folgenden Eigenschaften (aus JavaScript)
     *
     * @param title
     * @param givenFrom
     * @param givenTo
     */
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
        InfoBox.showMessage("Audio wurde erfolgreich bearbeitet !");
        MoreInformationHandler.clear();
    }

    /**
     * Erstellen einer Neuen Sendung (aus JavaScript)
     *
     * @param givenFrom
     * @param givenTo
     * @param title
     * @param intro
     * @param description
     */
    public void newBroadcast(long givenFrom, long givenTo, String title, String intro, String description) {
        logger.info("Sendung wird erzeugt ...");
        Date from = new Date(givenFrom);
        Date to = new Date(givenTo);
        BroadcastMessage broadcastMessage = new BroadcastMessage(from, to, title, intro, description);
        jmsTemplate.convertAndSend("broadcast", broadcastMessage);
        InfoBox.showMessage("Sendung wurde erfolgreich erstellt !");
        MoreInformationHandler.clear();
    }

    /**
     * rechnet Zeiten azf das korrekte Datum um
     *
     * @param givenFrom
     * @param givenTo
     * @param cFrom
     * @param cTo
     */
    private void getGivenTimes(long givenFrom, long givenTo, Calendar cFrom, Calendar cTo) {
        cFrom.setTime(new Date(givenFrom));
        cTo.setTime(new Date(givenTo));
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(currentBroadcastMessage.getStart());
        cFrom.set(cStart.get(Calendar.YEAR), cStart.get(Calendar.MONTH), cStart.get(Calendar.DATE));
        cTo.set(cStart.get(Calendar.YEAR), cStart.get(Calendar.MONTH), cStart.get(Calendar.DATE));
    }
}

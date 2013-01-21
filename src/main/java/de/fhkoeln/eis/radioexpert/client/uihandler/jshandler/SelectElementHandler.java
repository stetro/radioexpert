package de.fhkoeln.eis.radioexpert.client.uihandler.jshandler;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.client.uihandler.MoreInformationHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.TimeLineHandler;
import de.fhkoeln.eis.radioexpert.messaging.TimeLineElement;
import de.fhkoeln.eis.radioexpert.messaging.messages.AudioMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.InterviewMessage;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;

/**
 * Verwaltet das selektieren von TimeLine Elementen
 * <p/>
 * User: Steffen Tröster
 * Date: 26.12.12
 * Time: 10:53
 */
public class SelectElementHandler {
    private Logger logger = LoggerFactory.getLogger(SelectElementHandler.class);
    private JmsTemplate jmsTemplate;

    public SelectElementHandler() {
        jmsTemplate = ClientApplication.context.getBean(JmsTemplate.class);
    }

    /**
     * Stellt ein Element mit dem Primärschlüssel dar (Erstelldatum)
     *
     * @param createdAt
     */
    public void showElement(long createdAt) {
        Date b = new Date(createdAt);
        b.setTime(createdAt);
        for (final TimeLineElement e : TimeLineHandler.timeLineElements) {
            if (b.equals(e.getCreatedAt())) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (e instanceof AudioMessage) {
                            MoreInformationHandler.showElement((AudioMessage) e);
                        }
                        if (e instanceof InterviewMessage) {
                            MoreInformationHandler.showElement((InterviewMessage) e);
                        }
                    }
                });
            }
        }
    }

    /**
     * Startet ein Timline Element
     *
     * @param createdAt
     */
    public void startElement(long createdAt) {
        logger.info("Element starten ...");
        Date b = new Date(createdAt);
        b.setTime(createdAt);
        for (final TimeLineElement e : TimeLineHandler.timeLineElements) {
            if (b.equals(e.getCreatedAt())) {
                e.setActive(true);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        sendTimeLineElement(e);
                    }
                });
            } else if (e.getActive()) {
                e.setActive(false);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        sendTimeLineElement(e);
                    }
                });
            }
        }
    }

    /**
     * Sendet ein bearbeitetes TimeLine Element zu allen Subscribern
     *
     * @param e
     */
    private void sendTimeLineElement(TimeLineElement e) {
        logger.info("TimeLineElement geändert, wird weitergegeben");
        if (e instanceof AudioMessage) {
            jmsTemplate.convertAndSend("audio", e);
        }
        if (e instanceof InterviewMessage) {
            jmsTemplate.convertAndSend("interview", e);
        }

    }
}

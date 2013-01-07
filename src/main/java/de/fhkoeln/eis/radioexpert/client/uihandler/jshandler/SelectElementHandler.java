package de.fhkoeln.eis.radioexpert.client.uihandler.jshandler;

import de.fhkoeln.eis.radioexpert.client.uihandler.MoreInformationHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.TimeLineHandler;
import de.fhkoeln.eis.radioexpert.messaging.TimeLineElement;
import de.fhkoeln.eis.radioexpert.messaging.messages.AudioMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.InterviewMessage;
import javafx.application.Platform;

import java.util.Date;

/**
 * Verwaltet das selektieren von TimeLine Elementen
 * <p/>
 * User: Steffen Tröster
 * Date: 26.12.12
 * Time: 10:53
 */
public class SelectElementHandler {

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
}

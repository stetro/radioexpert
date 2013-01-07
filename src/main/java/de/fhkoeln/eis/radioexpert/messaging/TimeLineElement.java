package de.fhkoeln.eis.radioexpert.messaging;

import java.util.Date;
import java.util.List;

/**
 * Gemeinschaft von TimeLine Elementen, dazu gehoeren:
 * ->AudioMessage
 * ->InterviewMessage
 * -> ...
 * TODO: Auffüllen
 * <p/>
 * User: Steffen Tröster
 * Date: 21.12.12
 * Time: 13:22
 */
public interface TimeLineElement {
    Date getCreatedAt();

    String getTitle();

    String getInfo();

    String getType();

    Date getStart();

    Date getEnd();

    void addMessage(String message);

    List<String> getMessages();
}

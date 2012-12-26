package de.fhkoeln.eis.radioexpert.messaging.messages;

import java.util.Date;

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
}

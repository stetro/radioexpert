package de.fhkoeln.eis.radioexpert.messaging.messages;

import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;
import java.util.Date;

/**
 * User: Steffen Tröster
 * Date: 07.11.12
 * Time: 17:35
 * Einfache Twitternachricht die vom TwitterService gesendet werden kann
 */
public class TwitterMessage implements Serializable {
    private String message;
    private String user;
    private Date time;

    public TwitterMessage(String message, String user, Date time) {
        this.message = message;
        this.user = user;
        this.time = time;
    }

    public TwitterMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).toString();
    }
}

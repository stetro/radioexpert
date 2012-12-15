package de.fhkoeln.eis.radioexpert.messaging.messages;

import java.io.Serializable;
import java.util.Date;

/**
 * Repraesentiert die Onlinebenachrichtigung eines Nutzers
 * <p/>
 * User: Steffen Tröster
 * Date: 15.12.12
 * Time: 13:55
 */
public class OnlineStatusMessage implements Serializable {
    private String user;
    private String role;
    private Date date = new Date();

    public OnlineStatusMessage(String user, String role) {
        this.user = user;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }
}

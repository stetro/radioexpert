package de.fhkoeln.eis.radioexpert.messaging.messages;

import de.fhkoeln.eis.radioexpert.client.util.UserRole;

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
    private UserRole role;
    private Date date = new Date();

    public OnlineStatusMessage(String user, UserRole role) {
        this.user = user;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

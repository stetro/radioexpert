package de.fhkoeln.eis.radioexpert.messaging.messages;

import de.fhkoeln.eis.radioexpert.messaging.SocialMediaMessage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Steffen Tröster
 * Date: 07.11.12
 * Time: 17:35
 * Einfache Twitternachricht die vom TwitterService gesendet werden kann
 */
@Entity
@Table(name = "TwitterMessage")
public class TwitterMessage implements Serializable, SocialMediaMessage {
    @Column(name = "message")
    private String message;
    @Column(name = "user")
    private String user;
    @Column(name = "time")
    private Date time;

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "broadcastCreatedAt")
    private Date broadcastCreatedAt;


    public TwitterMessage(String message, String user, Date time, long id, Date broadcastCreatedAt) {
        this.message = message;
        this.user = user;
        this.time = time;
        this.id = id;
        this.broadcastCreatedAt = broadcastCreatedAt;
    }

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

    @Override
    public String getSender() {
        return user;
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
        return getMessage();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBroadcastCreatedAt() {
        return broadcastCreatedAt;
    }

    public void setBroadcastCreatedAt(Date broadcastCreatedAt) {
        this.broadcastCreatedAt = broadcastCreatedAt;
    }
}

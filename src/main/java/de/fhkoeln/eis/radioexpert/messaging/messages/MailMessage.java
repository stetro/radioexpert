package de.fhkoeln.eis.radioexpert.messaging.messages;

import de.fhkoeln.eis.radioexpert.messaging.SocialMediaMessage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Steffen Tröster
 * Date: 13.11.12
 * Time: 11:12
 * Mail POJO mit Hibernate Anbindung
 */
@Entity
@Table(name = "MailMessages")
public class MailMessage implements Serializable, SocialMediaMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private long id;
    @Column(name = "message")
    private String message;
    @Column(name = "sender")
    private String sender;
    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "broadcastCreatedAt")
    private Date broadcastCreatedAt;
    private String title;

    public MailMessage(long id, String message, String sender, Date timestamp, Date broadcastCreatedAt) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
        this.broadcastCreatedAt = broadcastCreatedAt;
    }

    public MailMessage(String message, String sender, Date timestamp) {
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public MailMessage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getBroadcastCreatedAt() {
        return broadcastCreatedAt;
    }

    public void setBroadcastCreatedAt(Date broadcastCreatedAt) {
        this.broadcastCreatedAt = broadcastCreatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package de.fhkoeln.eis.radioexpert.messaging.messages;

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
public class MailMessage implements Serializable {
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
}

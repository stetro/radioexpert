package de.fhkoeln.eis.radioexpert.messaging.messages;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Einfache Chatnachricht
 * User: Steffen Tröster
 * Date: 26.11.12
 * Time: 10:38
 */
@Entity
@Table(name = "ChatMessages")
public class ChatMessage implements Serializable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "message")
    private String message;
    @Column(name = "sender")
    private String sender;


    public ChatMessage() {
    }

    public ChatMessage(String message, String sender, Date time) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.time = time;
    }

    @Column(name = "time")
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
}

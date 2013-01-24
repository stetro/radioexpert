package de.fhkoeln.eis.radioexpert.messaging.messages;

import de.fhkoeln.eis.radioexpert.messaging.TimeLineElement;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Repraesentiert ein Audioinhalt auf der Timeline (zB einen Song ect.)
 * <p/>
 * User: Steffen Tröster
 * Date: 21.12.12
 * Time: 12:04
 */
@Entity
@Table(name = "AudioMessage")
public class AudioMessage implements Serializable, TimeLineElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private Date createdAt;
    private Date start;
    private Date end;
    private String title;
    private Date broadcastCreatedAt;
    private String messages = "";
    private boolean active;

    public AudioMessage() {
    }

    public AudioMessage(String title, Date from, Date to) {
        this.title = title;
        this.start = from;
        this.end = to;
        this.createdAt = new Date();
        this.broadcastCreatedAt = BroadcastMessage.lastCreatedBroadcastDate;
    }

    public String getTitle() {
        return "<img src=\"img/audio.png\" />";
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public String getInfo() {
        return title;
    }

    @Override
    public String getType() {
        return "audio";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public void addMessage(String message) {
        this.messages += message + TimeLineElement.CUTTER;
    }

    @Override
    public String[] getMessages() {
        String[] split = messages.split(TimeLineElement.CUTTER);
        return split;

    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getBroadcastCreatedAt() {
        return broadcastCreatedAt;
    }

    public void setBroadcastCreatedAt(Date broadcastCreatedAt) {
        this.broadcastCreatedAt = broadcastCreatedAt;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

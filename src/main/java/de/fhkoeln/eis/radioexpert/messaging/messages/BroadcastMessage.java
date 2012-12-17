package de.fhkoeln.eis.radioexpert.messaging.messages;

import org.hibernate.annotations.Entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Broadcast Instanz Repraesentiert "Sendung"
 * <p/>
 * User: Steffen Tröster
 * Date: 17.12.12
 * Time: 09:29
 */
@Entity()
@Table(name = "BroadcastMessage")
public class BroadcastMessage {
    @javax.persistence.Id
    @Column(name = "createdAt")
    private final Date createdAt;
    @Column(name = "from")
    private Date from;
    @Column(name = "to")
    private Date to;
    @Column(name = "title")
    private String title;
    @Column(name = "intro")
    private String intro;
    @Column(name = "description")
    private String description;

    public BroadcastMessage(Date from, Date to, String title, String intro, String description) {
        createdAt = new Date();
        this.from = from;
        this.to = to;
        this.title = title;
        this.intro = intro;
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

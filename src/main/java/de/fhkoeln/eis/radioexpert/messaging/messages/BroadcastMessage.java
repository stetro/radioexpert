package de.fhkoeln.eis.radioexpert.messaging.messages;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Broadcast Instanz Repraesentiert "Sendung"
 * <p/>
 * User: Steffen Tröster
 * Date: 17.12.12
 * Time: 09:29
 */
@Entity
@Table(name = "BroadcastMessage")
public class BroadcastMessage implements Serializable {
    @javax.persistence.Id
    @Column(name = "createdAt")
    private Date createdAt;
    @Column(name = "start")
    private Date start;
    @Column(name = "end")
    private Date end;
    @Column(name = "title")
    private String title;
    @Column(name = "intro")
    private String intro;
    @Column(name = "description")
    private String description;
    public static Date lastCreatedBroadcastDate;


    public BroadcastMessage() {
    }

    public BroadcastMessage(Date start, Date end, String title, String intro, String description) {
        createdAt = new Date();
        lastCreatedBroadcastDate = createdAt;
        this.start = start;
        this.end = end;
        this.title = title;
        this.intro = intro;
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
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

    public void setEnd(Date end) {
        this.end = end;
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

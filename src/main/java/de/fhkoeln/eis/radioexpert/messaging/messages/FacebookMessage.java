package de.fhkoeln.eis.radioexpert.messaging.messages;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Steffen Tröster
 * Date: 12.11.12
 * Time: 11:40
 * Facebook Message POJO speichert Daten fuer die letzte Facebookkommunikation
 */
@Entity
@Table(name = "FacebookMessages")
public class FacebookMessage implements Serializable, SocialMediaMessage {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "facebookId")
    private String facebookId;
    @Column(name = "updatedTime")
    private Date updatedTime;
    @Column(name = "message")
    private String message;
    @Column(name = "user")
    private String user;
    @Column(name = "likes")
    private int likes;

    @Column(name = "broadcastCreatedAt")
    private Date broadcastCreatedAt;

    public FacebookMessage(long id, String facebookId, Date updatedTime, String message, String user, int likes, Date broadcastCreatedAt) {
        this.id = id;
        this.facebookId = facebookId;
        this.updatedTime = updatedTime;
        this.message = message;
        this.user = user;
        this.likes = likes;
        this.broadcastCreatedAt = broadcastCreatedAt;
    }

    public FacebookMessage() {
    }

    public FacebookMessage(long id, String facebookId, Date updatedTime, String message, String user, int likes) {
        this.id = id;
        this.facebookId = facebookId;
        this.updatedTime = updatedTime;
        this.message = message;
        this.user = user;
        this.likes = likes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getBroadcastCreatedAt() {
        return broadcastCreatedAt;
    }

    public void setBroadcastCreatedAt(Date broadcastCreatedAt) {
        this.broadcastCreatedAt = broadcastCreatedAt;
    }
}

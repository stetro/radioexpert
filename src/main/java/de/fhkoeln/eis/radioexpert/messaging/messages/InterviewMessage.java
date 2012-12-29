package de.fhkoeln.eis.radioexpert.messaging.messages;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repräsentiert die Timeline Elemente in der UI
 * <p/>
 * User: Steffen Tröster
 * Date: 18.12.12
 * Time: 09:54
 */

@Entity
@Table(name = "InterviewMessage")
public class InterviewMessage implements Serializable, TimeLineElement {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "createdAt")
    private Date createdAt;
    @Column(name = "start")
    private Date start;
    @Column(name = "end")
    private Date end;
    @Column(name = "title")
    private String title;
    @Column(name = "phone")
    private String phone;
    @Column(name = "street")
    private String street;
    @Column(name = "town")
    private String town;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "info")
    private String info;
    @Column(name = "talkInfo")
    private String talkInfo;
    @Column(name = "questions")
    private String questions;
    @Column(name = "socialMedia")
    private String socialMedia;
    @Column(name = "quotes")
    private String quotes;
    @Column(name = "broadcastCreatedAt")
    private Date broadcastCreatedAt;

    @ElementCollection
    private List<String> messages = new ArrayList<String>();

    public InterviewMessage() {
    }

    public InterviewMessage(Date createdAt, Date start, Date end, String title, String phone, String street, String town, String email, String name, String info, String talkInfo, String questions, String socialMedia, String quotes, Date broadcastCreatedAt) {
        this.createdAt = createdAt;
        this.start = start;
        this.end = end;
        this.title = title;
        this.phone = phone;
        this.street = street;
        this.town = town;
        this.email = email;
        this.name = name;
        this.info = info;
        this.talkInfo = talkInfo;
        this.questions = questions;
        this.socialMedia = socialMedia;
        this.quotes = quotes;
        this.broadcastCreatedAt = broadcastCreatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        this.messages.add(message);
    }

    @Override
    public List<String> getMessages() {
        return messages;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String getType() {
        return "interview";
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTalkInfo() {
        return talkInfo;
    }

    public void setTalkInfo(String talkInfo) {
        this.talkInfo = talkInfo;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getBroadcastCreatedAt() {
        return broadcastCreatedAt;
    }

    public void setBroadcastCreatedAt(Date broadcastCreatedAt) {
        this.broadcastCreatedAt = broadcastCreatedAt;
    }
}

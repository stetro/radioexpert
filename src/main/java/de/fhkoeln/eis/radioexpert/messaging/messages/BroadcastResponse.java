package de.fhkoeln.eis.radioexpert.messaging.messages;

import java.io.Serializable;
import java.util.List;

/**
 * Beinhaltet alle Komponenten einer Sendung um diese im Nachhinein zu laden
 * <p/>
 * User: Steffen Tröster
 * Date: 28.12.12
 * Time: 09:25
 */
public class BroadcastResponse implements Serializable {
    private BroadcastMessage broadcastMessage;
    private List<SocialMediaMessage> socialMediaMessages;
    private List<ChatMessage> chatMessages;
    private List<TimeLineElement> timeLineElements;

    public BroadcastResponse() {
    }

    public BroadcastResponse(BroadcastMessage broadcastMessage, List<SocialMediaMessage> socialMediaMessages, List<ChatMessage> chatMessages, List<TimeLineElement> timeLineElements) {
        this.broadcastMessage = broadcastMessage;
        this.socialMediaMessages = socialMediaMessages;
        this.chatMessages = chatMessages;
        this.timeLineElements = timeLineElements;
    }

    public BroadcastMessage getBroadcastMessage() {
        return broadcastMessage;
    }

    public void setBroadcastMessage(BroadcastMessage broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    public List<SocialMediaMessage> getSocialMediaMessages() {
        return socialMediaMessages;
    }

    public void setSocialMediaMessages(List<SocialMediaMessage> socialMediaMessages) {
        this.socialMediaMessages = socialMediaMessages;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public List<TimeLineElement> getTimeLineElements() {
        return timeLineElements;
    }

    public void setTimeLineElements(List<TimeLineElement> timeLineElements) {
        this.timeLineElements = timeLineElements;
    }
}

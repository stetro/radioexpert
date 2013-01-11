package de.fhkoeln.eis.radioexpert.server.messagelistener;

import de.fhkoeln.eis.radioexpert.messaging.SocialMediaMessage;
import de.fhkoeln.eis.radioexpert.messaging.TimeLineElement;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastResponse;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DatabaseController ist ein Service der auf die MessageQueue persistence.request horcht und
 * die Antwort auf persistence.response schickt.
 * Date: 21.11.12
 * Time: 14:17
 */
@Service
public class DatabaseController implements MessageListener {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    private static Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    @Override
    public void onMessage(Message message) {
        Session s = sessionFactory.openSession();
        BroadcastResponse broadcastResponse = buildUpBroadcastResponse(s);
        s.close();

        jmsTemplate.setPubSubDomain(false);
        jmsTemplate.convertAndSend("persistenceResponse", broadcastResponse);
        jmsTemplate.setPubSubDomain(true);
        logger.info("Abfragenachricht wurde empfangen  !!");
    }

    private BroadcastResponse buildUpBroadcastResponse(Session s) {
        BroadcastResponse broadcastResponse = new BroadcastResponse();
        broadcastResponse.setBroadcastMessage(DatabaseListener.currentBroadcast);
        if (broadcastResponse.getBroadcastMessage() != null) {
            Date createdAt = broadcastResponse.getBroadcastMessage().getCreatedAt();

            List chatMessages = s.createQuery("from ChatMessage where broadcastCreatedAt = :date").setTimestamp("date", createdAt).list();
            List twitterMessages = s.createQuery("from TwitterMessage where broadcastCreatedAt = :date").setTimestamp("date", createdAt).list();
            List facebookMessages = s.createQuery("from FacebookMessage where broadcastCreatedAt = :date").setTimestamp("date", createdAt).list();
            List mailMessages = s.createQuery("from MailMessage where broadcastCreatedAt = :date").setTimestamp("date", createdAt).list();

            List audioMessages = s.createQuery("from AudioMessage where broadcastCreatedAt = :date").setTimestamp("date", createdAt).list();
            List interviewMessages = s.createQuery("from InterviewMessage where broadcastCreatedAt = :date").setTimestamp("date", createdAt).list();

            List socialMediaMessages = new ArrayList<SocialMediaMessage>();
            List timeLineElements = new ArrayList<TimeLineElement>();

            timeLineElements.addAll(audioMessages);
            timeLineElements.addAll(interviewMessages);
            socialMediaMessages.addAll(twitterMessages);
            socialMediaMessages.addAll(facebookMessages);
            socialMediaMessages.addAll(mailMessages);


            broadcastResponse.setChatMessages(chatMessages);
            broadcastResponse.setSocialMediaMessages(socialMediaMessages);
            broadcastResponse.setTimeLineElements(timeLineElements);
        }
        return broadcastResponse;
    }
}
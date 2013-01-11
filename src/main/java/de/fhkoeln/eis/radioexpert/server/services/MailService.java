package de.fhkoeln.eis.radioexpert.server.services;

import de.fhkoeln.eis.radioexpert.messaging.messages.MailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Date: 13.11.12
 * Time: 11:12
 * Fragt aktuelle Mails ab
 */
@Service
public class MailService implements RadioExpertService {

    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    private long pollInterval = 1000 * 20;
    private String host = "mail2.goracer.de";
    private String password = "eis";
    private String userName = "eis@stetro-blog.de";

    @Override
    public void start() {
        logger.info("MailService wird gestartet ...");
        jmsTemplate.setPubSubDomain(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        imapQuery();
                        Thread.sleep(pollInterval);
                    } catch (Exception e) {
                        logger.error("Fehler beim starten des Servers ! :" + e.getMessage());
                        break;
                    }
                }
            }
        }).start();
    }

    private void imapQuery() throws MessagingException, IOException {
        Session session = Session.getDefaultInstance(System.getProperties(), null);
        System.out.println("get store..");
        Store store = session.getStore("imap");
        System.out.println("connect..");
        store.connect(host, userName, password);
        System.out.println("get default folder ..");
        Folder folder = store.getDefaultFolder();
        folder = folder.getFolder("inbox");
        folder.open(Folder.READ_ONLY);
        System.out.println("reading messages..");
        Message[] messages = folder.getMessages();
        for (Message m : messages) {
            String sender;
            sender = m.getFrom()[0].toString();
            MailMessage mailMessage;
            if (m.getContent() instanceof Multipart) {
                String content = "";
                Multipart mu = (Multipart) m.getContent();
                for (int x = 0; x < mu.getCount(); x++) {
                    BodyPart bodyPart = mu.getBodyPart(x);
                    String disposition = bodyPart.getDisposition();
                    if (disposition != null && (disposition.equals(BodyPart.ATTACHMENT))) {
                        // Mit Anhang
                    } else {
                        content += bodyPart.getContent();
                    }
                }
                mailMessage = new MailMessage(content, m.getFrom()[0].toString(), new Date());
            } else {
                mailMessage = new MailMessage(m.getContent().toString(), sender, new Date());
            }
            mailMessage.setTitle(m.getSubject());
            jmsTemplate.convertAndSend("mail", mailMessage);
            logger.info("Nachricht !!" + mailMessage.getMessage());
        }
    }
}

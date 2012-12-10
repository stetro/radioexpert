package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.messaging.messages.FacebookMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.MailMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.TwitterMessage;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import org.springframework.jms.core.JmsTemplate;

/**
 * Abboniert die Social Topics und stellt diese in der ListView dar
 * User: Steffen Tröster
 * Date: 04.12.12
 * Time: 15:26
 * TODO: Bessere Darstellung der Social Media Elemenete in der ListView
 */
public class SocialMediaListHandler {
    private ListView<String> socialListView;
    private JmsTemplate jmsTemplate;

    public SocialMediaListHandler(ListView<String> socialListView) {
        this.socialListView = socialListView;
        loadJmsTemplate();
        subscribeMailMessages();
        subscribeTwitterMessage();
        subscribeFacebookMessage();
    }

    private void subscribeFacebookMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Platform.isImplicitExit()) {
                    final FacebookMessage facebookMessage = (FacebookMessage) jmsTemplate.receiveAndConvert("facebook");
                    displayFacebookMessage(facebookMessage);
                }
            }
        }).start();
    }

    private void subscribeTwitterMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Platform.isImplicitExit()) {
                    final TwitterMessage twitterMessage = (TwitterMessage) jmsTemplate.receiveAndConvert("twitter");
                    displayTwitterMessage(twitterMessage);
                }
            }
        }).start();

    }

    private void subscribeMailMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Platform.isImplicitExit()) {
                    final MailMessage mailMessage = (MailMessage) jmsTemplate.receiveAndConvert("mail");
                    displayMailMessage(mailMessage);
                }
            }
        }).start();
    }

    public void displayFacebookMessage(final FacebookMessage facebookMessage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socialListView.getItems().add(facebookMessage.getMessage());
            }
        });
    }

    public void displayTwitterMessage(final TwitterMessage twitterMessage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socialListView.getItems().add(twitterMessage.getMessage());
            }
        });
    }

    public void displayMailMessage(final MailMessage mailMessage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socialListView.getItems().add(mailMessage.getMessage());
            }
        });
    }

    private void loadJmsTemplate() {
        jmsTemplate = (JmsTemplate) ClientApplication.context.getBean("jmsTemplate");
        jmsTemplate.setPubSubDomain(true);
    }
}

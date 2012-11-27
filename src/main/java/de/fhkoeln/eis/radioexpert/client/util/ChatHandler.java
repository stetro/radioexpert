package de.fhkoeln.eis.radioexpert.client.util;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.messaging.messages.ChatMessage;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

/**
 * Abboniert chat und Stellt Nachrichten in WebView dar
 * User: Steffen Tröster
 * Date: 26.11.12
 * Time: 10:38
 */
public class ChatHandler {
    private JmsTemplate jmsTemplate;
    private WebView chatWebView;
    private TextField chatTextField;

    private static Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    public ChatHandler(WebView chatWebView, TextField chatTextField) {
        this.chatWebView = chatWebView;
        this.chatTextField = chatTextField;

        jmsTemplate = ClientApplication.context.getBean(JmsTemplate.class);
        try {
            setupMessageListener();
            setupKeyListener();
        } catch (JMSException e) {
            logger.error("Fehler beim Empfangen von Chatnachrichten");
        }
    }

    private void setupKeyListener() {
        this.chatTextField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    jmsTemplate.convertAndSend("chat", new ChatMessage("Blaa", "userxy"));
                    logger.info("Nachricht " + chatTextField.getText() + " gesendet");
                    chatTextField.setText("");
                }
            }
        });
    }

    private void setupMessageListener() throws JMSException {

        final Connection connection = jmsTemplate.getConnectionFactory().createConnection();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();

        Topic topic = session.createTopic("chat");
        MessageConsumer consumer = session.createConsumer(topic);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                logger.info("Chat Nachricht Empfangen !");
                try {
                    ChatMessage chatMessage = (ChatMessage) message.getObjectProperty("object");
                    chatWebView.getEngine().loadContent("FooBar");
                    logger.info(chatMessage.toString());
                } catch (JMSException e) {
                    logger.error("Nachricht konnte nicht empfangen werden !");
                }
            }
        });
    }

}

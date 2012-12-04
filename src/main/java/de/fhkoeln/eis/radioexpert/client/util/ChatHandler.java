package de.fhkoeln.eis.radioexpert.client.util;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.messaging.messages.ChatMessage;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import java.net.URL;
import java.util.Date;

/**
 * Abboniert chat und Stellt Nachrichten in WebView dar
 * User: Steffen Tröster
 * Date: 26.11.12
 * Time: 10:38
 */
public class ChatHandler {
    private final JmsTemplate jmsTemplate;
    private WebView chatWebView;
    private TextField chatTextField;

    private static Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    public ChatHandler(WebView chatWebView, TextField chatTextField) {
        this.chatWebView = chatWebView;
        this.chatTextField = chatTextField;
        jmsTemplate = ClientApplication.context.getBean(JmsTemplate.class);
        jmsTemplate.setPubSubDomain(true);
        try {
            setupMessageListener();
            setupKeyListener();
            loadLocalChatHtmlComponent();
        } catch (JMSException e) {
            logger.error("Fehler beim Empfangen von Chatnachrichten");
        }
    }

    private void loadLocalChatHtmlComponent() {
        chatWebView.getEngine().setJavaScriptEnabled(true);
        URL url = getClass().getResource("/gui/component/chat.html");
        chatWebView.getEngine().load(url.toExternalForm());
    }


    private void setupKeyListener() {
        this.chatTextField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    jmsTemplate.convertAndSend("chat", new ChatMessage(chatTextField.getText(), ClientApplication.user, new Date()));
                    logger.info("Nachricht " + chatTextField.getText() + " gesendet");
                    chatTextField.setText("");
                }
            }
        });
    }

    private void setupMessageListener() throws JMSException {
        jmsTemplate.setPubSubDomain(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Platform.isImplicitExit()) {
                    final ChatMessage chatMessage = (ChatMessage) jmsTemplate.receiveAndConvert("chat");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            String script = "addMessage('" + chatMessage.getMessage() + "','" + chatMessage.getSender() + "','" + chatMessage.getTime().getTime() + "')";
                            logger.info("Nachricht empfangen " + chatMessage.getMessage());
                            chatWebView.getEngine().executeScript(script);
                        }
                    });
                }
            }
        }).start();
    }
}

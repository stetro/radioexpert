package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.messaging.messages.ChatMessage;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
    private Button chatButton;

    private static Logger logger = LoggerFactory.getLogger(ChatHandler.class);
    private static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");

    public ChatHandler(WebView chatWebView, TextField chatTextField, Button chatButton) {
        this.chatWebView = chatWebView;
        this.chatTextField = chatTextField;
        this.chatButton = chatButton;
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
                    sendMessageFromInputField();
                }
            }
        });
        this.chatButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sendMessageFromInputField();
            }
        });
    }

    private void sendMessageFromInputField() {
        jmsTemplate.convertAndSend("chat", new ChatMessage(chatTextField.getText(), ClientApplication.user, new Date()));
        logger.info("Nachricht " + chatTextField.getText() + " gesendet");
        chatTextField.setText("");
    }

    private void setupMessageListener() throws JMSException {
        jmsTemplate.setPubSubDomain(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Platform.isImplicitExit()) {
                    final ChatMessage chatMessage = (ChatMessage) jmsTemplate.receiveAndConvert("chat");
                    displayChatMessage(chatMessage);
                }
            }
        }).start();
    }

    private void displayChatMessage(final ChatMessage chatMessage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String script = "addMessage('" + chatMessage.getMessage() + "','" + chatMessage.getSender() + "','" + format.format(chatMessage.getTime().getTime()) + "')";
                logger.info("Nachricht empfangen " + chatMessage.getMessage());
                chatWebView.getEngine().executeScript(script);
            }
        });
    }
}

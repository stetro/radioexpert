package de.fhkoeln.eis.radioexpert.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Verwaltet alle UI Elemente der form.xml View (MVC)
 * User: Steffen Tröster
 * Date: 21.11.12
 * Time: 19:08
 */
@Service
public class ClientGUIController implements Initializable,MessageListener {

    @FXML
    WebView chatWebView;

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = chatWebView.getEngine();
        webEngine.setJavaScriptEnabled(true);
    }

    @Override
    public void onMessage(Message message) {
        chatWebView.getEngine().loadContent("gnaaa");
        System.out.println("goo");
    }
}

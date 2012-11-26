package de.fhkoeln.eis.radioexpert.client;

import de.fhkoeln.eis.radioexpert.client.util.BroadcastLoader;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
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
public class ClientGUIController extends Application implements Initializable {

    @FXML
    public WebView chatWebView;
    @FXML
    public MenuItem loadCurrentMenuItem;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/form.fxml"));
        stage.setTitle("RadioExpert - Client Anwendung ");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    private static Logger logger = LoggerFactory.getLogger(ClientGUIController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final WebEngine webEngine = chatWebView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        loadCurrentMenuItem.setOnAction(new BroadcastLoader());
    }

    public void startClientGUI(String[] args) {
        launch(args);
    }
}

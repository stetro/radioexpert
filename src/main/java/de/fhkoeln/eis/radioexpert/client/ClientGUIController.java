package de.fhkoeln.eis.radioexpert.client;

import de.fhkoeln.eis.radioexpert.client.util.BroadcastLoader;
import de.fhkoeln.eis.radioexpert.client.util.ChatHandler;
import de.fhkoeln.eis.radioexpert.client.util.SocialMediaListHandler;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    public TextField chatTextField;
    @FXML
    public WebView chatWebView;
    public ChatHandler chatHandler;
    @FXML
    public Button chatButton;
    @FXML
    public MenuItem loadCurrentMenuItem;
    @FXML
    public ListView socialListView;
    public SocialMediaListHandler socialMediaListHandler;

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
        loadCurrentMenuItem.setOnAction(new BroadcastLoader());
        chatHandler = new ChatHandler(chatWebView, chatTextField,chatButton);
        socialMediaListHandler = new SocialMediaListHandler(socialListView);
    }

    public void startClientGUI(String[] args) {
        launch(args);
    }
}

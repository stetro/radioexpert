package de.fhkoeln.eis.radioexpert.client.ui;

import de.fhkoeln.eis.radioexpert.client.uihandler.ChatHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.OnlineStatusHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.SocialMediaListHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.TimeLineHandler;
import de.fhkoeln.eis.radioexpert.client.util.BroadcastLoader;
import de.fhkoeln.eis.radioexpert.messaging.messages.OnlineStatusMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.SocialMediaMessage;
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

    private static Logger logger = LoggerFactory.getLogger(ClientGUIController.class);
    /*
    TimeLineHandler Variablen
     */
    @FXML
    public WebView timeLineWebView;
    private TimeLineHandler timeLineHandler;
    /*
    ChatHandler Variablen
     */
    @FXML
    public TextField chatTextField;
    @FXML
    public WebView chatWebView;
    @FXML
    public Button chatButton;
    private ChatHandler chatHandler;
    @FXML
    public MenuItem loadCurrentMenuItem;
    /*
    SocialMediaListHandler Variablen
     */
    @FXML
    public ListView<SocialMediaMessage> socialListView;
    private SocialMediaListHandler socialMediaListHandler;
    /*
    OnlineStatusHandler Variablen
    */
    @FXML
    public ListView<OnlineStatusMessage> onlineStatusListView;
    private OnlineStatusHandler onlineStatusHandler;


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/form.fxml"));
        stage.setTitle("RadioExpert - Client Anwendung ");
        stage.setScene(new Scene(root, 1000, 700));
        stage.show();
        logger.info("UI wurde gestartet !");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCurrentMenuItem.setOnAction(new BroadcastLoader());
        chatHandler = new ChatHandler(chatWebView, chatTextField, chatButton);
        socialMediaListHandler = new SocialMediaListHandler(socialListView);
        timeLineHandler = new TimeLineHandler(timeLineWebView);
        onlineStatusHandler = new OnlineStatusHandler(onlineStatusListView);
    }

    public void startClientGUI(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }
}

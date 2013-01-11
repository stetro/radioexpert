package de.fhkoeln.eis.radioexpert.client.ui;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.client.uihandler.*;
import de.fhkoeln.eis.radioexpert.client.util.BroadcastLoader;
import de.fhkoeln.eis.radioexpert.client.util.InfoBox;
import de.fhkoeln.eis.radioexpert.client.util.UserRole;
import de.fhkoeln.eis.radioexpert.messaging.SocialMediaMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.OnlineStatusMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Verwaltet alle UI Elemente der form.xml View (MVC)
 * <p/>
 * Date: 21.11.12
 * Time: 19:08
 */
@Service
public class ClientGUIController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(ClientGUIController.class);
    /*
    TimeLineHandler Variablen
     */
    @FXML
    private WebView timeLineWebView;
    @FXML
    private MenuButton addElementDropDownMenuButton;
    private TimeLineHandler timeLineHandler;
    /*
    ChatHandler Variablen
     */
    @FXML
    private TextField chatTextField;
    @FXML
    private WebView chatWebView;
    @FXML
    private Button chatButton;
    private ChatHandler chatHandler;
    @FXML
    private MenuItem loadCurrentMenuItem;
    /*
    SocialMediaListHandler Variablen
     */
    @FXML
    private ListView<SocialMediaMessage> socialListView;
    @FXML
    private AnchorPane socialAnchorPane;
    private SocialMediaListHandler socialMediaListHandler;
    /*
    OnlineStatusHandler Variablen
    */
    @FXML
    private ListView<OnlineStatusMessage> onlineStatusListView;
    private OnlineStatusHandler onlineStatusHandler;
    /*
    MoreInformationHandler Variablen (Mittelteil der GUI)
     */
    @FXML
    private WebView moreInformationWebView;
    private MoreInformationHandler moreInformationHandler;
    /*
    Verschiedene GUI Komponenten
     */
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem newBroadcastMenuItem;
    /*
    Infoboxvariablen
    */
    @FXML
    private Text infoText;
    @FXML
    private Rectangle infoRectangle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeLineHandler = new TimeLineHandler(timeLineWebView, addElementDropDownMenuButton);
        chatHandler = new ChatHandler(chatWebView, chatTextField, chatButton);
        socialMediaListHandler = new SocialMediaListHandler(socialListView);
        onlineStatusHandler = new OnlineStatusHandler(onlineStatusListView);
        moreInformationHandler = new MoreInformationHandler(moreInformationWebView);
        loadCurrentMenuItem.setOnAction(new BroadcastLoader());
        InfoBox.setVariables(infoText, infoRectangle);
        setupOtherGUIComponents();
    }

    public void startClientGUI(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/form.fxml"));
        stage.hide();
        stage.setTitle("RadioExpert - Client Anwendung ");
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add("gui/style.css");
        stage.setScene(scene);
        stage.show();
        logger.info("Client UI wurde gestartet !");
    }

    private void setupOtherGUIComponents() {
        closeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
        if (ClientApplication.role != UserRole.REDAKTEUR && socialAnchorPane != null) {

            socialAnchorPane.setPrefHeight(0);
            socialAnchorPane.setVisible(false);
        }

        // Nur Redaktion darf Sendung und Elemente hinzufuegen
        if (ClientApplication.role == UserRole.REDAKTEUR) {
            newBroadcastMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    moreInformationHandler.showNewBroadcast();
                }
            });
        } else {
            newBroadcastMenuItem.setVisible(false);
        }
    }
}

package de.fhkoeln.eis.radioexpert.client.ui;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.client.uihandler.ServerStatusHandler;
import de.fhkoeln.eis.radioexpert.client.util.UserRole;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Managed LoginDialogue mit Serverstatus Funktion
 * <p/>
 * User: Steffen Tröster
 * Date: 15.12.12
 * Time: 15:02
 */
@Service
public class LoginDialogueController implements Initializable {

    @FXML
    public ComboBox<String> roleComboBox;
    @FXML
    public TextField nameTextField;
    @FXML
    public Button submitButton;
    @FXML
    public Text serverStatusText;
    @FXML
    public ImageView logoImageView;

    private Logger logger = LoggerFactory.getLogger(LoginDialogueController.class);

    public void startLoginGui(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/login.fxml"));
        stage.setTitle("Login Dialog");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
        logger.info("LoginDialogue wurde gestartet !");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLogoImage();
        setComboBoxContent();
        submitButtonBindingAndApplicationStart();
        showServerStatus();
    }

    private void showLogoImage() {
        logoImageView.setImage(new Image("gui/component/img/logo.png"));
    }

    private void submitButtonBindingAndApplicationStart() {
        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String name = nameTextField.getText();
                if (name.length() <= 2 || !ServerStatusHandler.serverIsAvailable()) {
                    serverStatusText.setText("Fehler bei der Anmeldung !");
                    return;
                }
                UserRole role = getSelectedRole();
                ClientApplication.runApplication("localhost", name, role);
            }
        });
    }

    private UserRole getSelectedRole() {
        for (UserRole u : UserRole.values()) {
            if (roleComboBox.getValue().equals(u.getFullname())) {
                return u;
            }
        }
        return UserRole.REDAKTEUR;
    }

    private void setComboBoxContent() {
        ObservableList<String> items = roleComboBox.getItems();
        items.clear();
        for (UserRole userRole : UserRole.values()) {
            items.add(userRole.getFullname());
        }
        roleComboBox.setValue(items.get(0));
    }

    private void showServerStatus() {
        if (ServerStatusHandler.serverIsAvailable()) {
            serverStatusText.setText(serverStatusText.getText() + " Verfügbar!");
        } else {
            serverStatusText.setText(serverStatusText.getText() + " Fehler bei der Verbindung!");
        }
    }


}

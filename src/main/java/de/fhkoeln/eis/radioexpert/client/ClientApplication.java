package de.fhkoeln.eis.radioexpert.client;

import de.fhkoeln.eis.radioexpert.client.ui.ClientGUIController;
import de.fhkoeln.eis.radioexpert.client.ui.LoginDialogueController;
import de.fhkoeln.eis.radioexpert.client.util.UserRole;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * Hauptklasse fuer die Clientseite mit GUI und ActiveMQ Anbindung
 * <p/>
 * User: Steffen Tröster
 * Date: 21.11.12
 * Time: 19:08
 */
public class ClientApplication extends Application {

    public static ApplicationContext context;
    public static String user;
    public static String host;
    public static UserRole role;
    public static Stage stage;

    public static void main(String[] args) {
        // Lade ApplicationContext und starte Application
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        launch(args);
    }

    /**
     * Callback wird von launch() Ausgefuehrt und startet zunaechst den Logindialog
     */
    @Override
    public void start(Stage givenStage) throws Exception {
        stage = givenStage;
        requestClientApplicationSettings();
    }

    /**
     * Login Dialog starten
     */
    private static void requestClientApplicationSettings() {
        LoginDialogueController loginDialogueController = (LoginDialogueController) context.getBean("loginDialogueController");
        try {
            loginDialogueController.startLoginGui(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wird beim beenden des Logindialog gestartet
     */
    public static void runApplication(String host, String user, UserRole role) {
        ClientApplication.user = user;
        ClientApplication.host = host;
        ClientApplication.role = role;

        ClientGUIController clientGUIController = (ClientGUIController) context.getBean("clientGUIController");
        try {
            clientGUIController.startClientGUI(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wenn das Fenster geschlossen wird
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(1);
    }
}
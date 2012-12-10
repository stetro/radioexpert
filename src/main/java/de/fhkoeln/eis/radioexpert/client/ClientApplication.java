package de.fhkoeln.eis.radioexpert.client;

import de.fhkoeln.eis.radioexpert.client.ui.ClientGUIController;
import de.fhkoeln.eis.radioexpert.client.ui.SimpleSettingsDialog;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * Hauptklasse fuer die Clientseite mit GUI und ActiveMQ Anbindung
 * User: Steffen Tröster
 * Date: 21.11.12
 * Time: 19:08
 */
public class ClientApplication {

    public static ApplicationContext context;
    private static String[] args;
    public static String user;
    public static String host;

    public static void runApplication(String host, String user, String role) {
        ClientApplication.user = user;
        ClientApplication.host = host;

        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClientGUIController clientGUIController = (ClientGUIController) context.getBean("clientGUIController");
        clientGUIController.startClientGUI(args);
    }

    public static void main(String[] args) {
        ClientApplication.args = args;
        requestClientApplicationSettings();
    }

    private static void requestClientApplicationSettings() {
        SimpleSettingsDialog dialog = new SimpleSettingsDialog();
    }
}
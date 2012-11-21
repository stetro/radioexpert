package de.fhkoeln.eis.radioexpert.client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hauptklasse fuer die Clientseite mit GUI und ActiveMQ Anbindung
 * User: Steffen Tröster
 * Date: 21.11.12
 * Time: 19:08
 */
public class ClientApplication extends Application {

    @FXML
    Line timeLineLine;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/form.fxml"));
        stage.setTitle("RadioExpert - Client Anwendung ");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        launch(args);
    }
}
package de.fhkoeln.eis.radioexpert.client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class ClientApplication extends Application {

    @FXML
    Line timeLineLine;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/form.fxml"));
        stage.setTitle("FXML Welcome");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
package de.fhkoeln.eis.radioexpert.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Steffen Tröster
 * Date: 07.11.12
 * Time: 09:58
 * To change this template use File | Settings | File Templates.
 */
public class TimeLineController implements Initializable {

    @FXML
    Line timeLineLine;
    @FXML
    Ellipse timeLineEllipse;

    @FXML
    ListView listListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print(timeLineLine.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        timeLineLine.setStartY(timeLineLine.getStartY() + 1);
                        timeLineLine.setEndY(timeLineLine.getEndY() + 1);

                        timeLineEllipse.setCenterY(timeLineEllipse.getCenterY() + 1);

                        synchronized (listListView.getItems()) {
                            listListView.getItems().add("Fooo");
                        }

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        break;
                    }
                }
            }
        }).start();
    }
}

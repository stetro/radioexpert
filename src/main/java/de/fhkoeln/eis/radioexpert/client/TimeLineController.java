package de.fhkoeln.eis.radioexpert.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Steffen Tröster
 * Date: 07.11.12
 * Time: 09:58
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TimeLineController implements Initializable {

    @Autowired
    private JmsTemplate jmsTemplate;

    @FXML
    Line timeLineLine;
    @FXML
    Ellipse timeLineEllipse;

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

                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        break;
                    }
                }
            }
        }).start();
    }
}

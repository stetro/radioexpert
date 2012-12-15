package de.fhkoeln.eis.radioexpert.client.uihandler;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.*;

/**
 * Verwaltet den OnlineStatus der Nutzer in der GUI
 * <p/>
 * User: Steffen Tröster
 * Date: 15.12.12
 * Time: 10:09
 */

public class OnlineStatusHandler {

    private static Map<String, Date> onlineStatus;
    private static final int delaySeconds = 20;
    private static ListView<String> onlineStatusListView;


    public OnlineStatusHandler(ListView<String> givenOnlineStatusListView) {
        onlineStatusListView = givenOnlineStatusListView;
        onlineStatusListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new OnlineStatusCell();
            }
        });
        onlineStatus = new HashMap<String, Date>();
    }

    public static void signPersonAsOnline(String user) {
        if (onlineStatus == null) return;
        onlineStatus.put(user, new Date());
    }

    public static void updateOnlineStatus() {
        if (onlineStatusListView == null) return;
        Date lastTime = new Date();
        lastTime.setTime(lastTime.getTime() - delaySeconds * 1000);
        List<String> offlineList = new ArrayList<String>();
        final List<String> onlineList = new ArrayList<String>();
        for (Map.Entry<String, Date> e : onlineStatus.entrySet()) {
            if (lastTime.after(e.getValue())) {
                offlineList.add(e.getKey());
            } else {
                onlineList.add(e.getKey());
            }
        }
        for (String user : offlineList) {
            onlineStatus.remove(user);
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<String> onlineUser = FXCollections.observableArrayList(onlineList);
                onlineStatusListView.setItems(onlineUser);
            }
        });
    }

    private class OnlineStatusCell extends ListCell<String> {
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            Rectangle rect = new Rectangle(15, 15);
            if (item != null) {
                rect.setFill(Color.web("#0E0"));

                HBox box = new HBox();
                box.setSpacing(10.0);
                box.getChildren().add(rect);
                box.getChildren().add(new Text(this.getItem()));
                setGraphic(box);

            }
        }
    }
}

package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.messaging.messages.OnlineStatusMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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

    private static Map<String, OnlineStatusMessage> onlineStatus;
    private static final int delaySeconds = 10;
    private static ListView<OnlineStatusMessage> onlineStatusListView;


    public OnlineStatusHandler(ListView<OnlineStatusMessage> givenOnlineStatusListView) {
        onlineStatusListView = givenOnlineStatusListView;
        onlineStatusListView.setCellFactory(new Callback<ListView<OnlineStatusMessage>, ListCell<OnlineStatusMessage>>() {
            @Override
            public ListCell<OnlineStatusMessage> call(ListView<OnlineStatusMessage> stringListView) {
                return new OnlineStatusCell();
            }
        });
        onlineStatus = new HashMap<String, OnlineStatusMessage>();
    }

    public static void signPersonAsOnline(OnlineStatusMessage onlineStatusMessage) {
        if (onlineStatus == null) return;
        onlineStatus.put(onlineStatusMessage.getUser(), onlineStatusMessage);
    }

    public static void updateOnlineStatus() {
        if (onlineStatusListView == null) return;

        List<String> offlineList = new ArrayList<String>();
        final List<OnlineStatusMessage> onlineList = new ArrayList<OnlineStatusMessage>();

        findOfflineAndOnlineUser(offlineList, onlineList);
        removeOfflineUser(offlineList);
        updateListInOnlineStatusListView(onlineList);
    }

    private static void updateListInOnlineStatusListView(final List<OnlineStatusMessage> onlineList) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<OnlineStatusMessage> onlineUser = FXCollections.observableArrayList(onlineList);
                onlineStatusListView.setItems(onlineUser);
            }
        });
    }

    private static void removeOfflineUser(List<String> offlineList) {
        for (String user : offlineList) {
            onlineStatus.remove(user);
        }
    }

    private static void findOfflineAndOnlineUser(List<String> offlineList, List<OnlineStatusMessage> onlineList) {
        Date lastTime = new Date();
        lastTime.setTime(lastTime.getTime() - delaySeconds * 1000);
        for (Map.Entry<String, OnlineStatusMessage> e : onlineStatus.entrySet()) {
            if (lastTime.after(e.getValue().getDate())) {
                offlineList.add(e.getKey());
            } else {
                onlineList.add(e.getValue());
            }
        }
    }

    private class OnlineStatusCell extends ListCell<OnlineStatusMessage> {
        public void updateItem(OnlineStatusMessage item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                ImageView imageView = new ImageView();
                switch (item.getRole().charAt(0)) {
                    case 'm':
                        imageView.setImage(new Image("gui/component/img/moderation.png"));
                        break;
                    case 'r':
                        imageView.setImage(new Image("gui/component/img/redaktion.png"));
                        break;
                    case 't':
                        imageView.setImage(new Image("gui/component/img/technik.png"));
                        break;
                }
                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);
                box.setSpacing(5.0);
                box.getChildren().add(imageView);
                box.getChildren().add(new Text(this.getItem().getUser()));
                this.setAlignment(Pos.CENTER);

                this.setWidth(40);

                setGraphic(box);

            }
        }
    }
}

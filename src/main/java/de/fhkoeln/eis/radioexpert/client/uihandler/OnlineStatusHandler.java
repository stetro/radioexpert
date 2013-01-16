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
 * Date: 15.12.12
 * Time: 10:09
 */

public class OnlineStatusHandler {

    private static Map<String, OnlineStatusMessage> onlineStatus;
    private static final int delaySeconds = 10;
    private static ListView<OnlineStatusMessage> onlineStatusListView;


    public OnlineStatusHandler(ListView<OnlineStatusMessage> givenOnlineStatusListView) {
        onlineStatusListView = givenOnlineStatusListView;
        // OnlineStatusCell stellt den Onlinestatus mit Grafik dar
        onlineStatusListView.setCellFactory(new Callback<ListView<OnlineStatusMessage>, ListCell<OnlineStatusMessage>>() {
            @Override
            public ListCell<OnlineStatusMessage> call(ListView<OnlineStatusMessage> stringListView) {
                return new OnlineStatusCell();
            }
        });
        onlineStatus = new HashMap<String, OnlineStatusMessage>();
    }

    /**
     * Fügt die Person der Liste aller Online Users hinzu
     *
     * @param onlineStatusMessage
     */
    public static void signPersonAsOnline(OnlineStatusMessage onlineStatusMessage) {
        if (onlineStatus == null) return;
        onlineStatus.put(onlineStatusMessage.getUser(), onlineStatusMessage);
    }

    /**
     * aktualisiert die Liste des Onlinestatus
     */
    public static void updateOnlineStatus() {
        if (onlineStatusListView == null) return;

        List<String> offlineList = new ArrayList<String>();
        final List<OnlineStatusMessage> onlineList = new ArrayList<OnlineStatusMessage>();

        findOfflineAndOnlineUser(offlineList, onlineList);
        removeOfflineUser(offlineList);
        updateListInOnlineStatusListView(onlineList);
    }

    /**
     * Aktualisiert die Anzeige (Thread im UI)
     *
     * @param onlineList
     */
    private static void updateListInOnlineStatusListView(final List<OnlineStatusMessage> onlineList) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<OnlineStatusMessage> onlineUser = FXCollections.observableArrayList(onlineList);
                onlineStatusListView.setItems(onlineUser);
            }
        });
    }

    /**
     * Löscht offline User aus der Liste der onlineuser
     *
     * @param offlineList
     */
    private static void removeOfflineUser(List<String> offlineList) {
        for (String user : offlineList) {
            onlineStatus.remove(user);
        }
    }

    /**
     * Suche nach einem Timeout eines Users
     *
     * @param offlineList
     * @param onlineList
     */
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

    /**
     * OnlineStatusCell stellt den Onlinestatus mit Grafik dar
     */
    private class OnlineStatusCell extends ListCell<OnlineStatusMessage> {
        public void updateItem(OnlineStatusMessage item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(item.getRole().getImagePath()));
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

package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.messaging.messages.SocialMediaMessage;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Abboniert die Social Topics und stellt diese in der ListView dar
 * <p/>
 * User: Steffen Tröster
 * Date: 04.12.12
 * Time: 15:26
 * TODO: Bessere Darstellung der Social Media Elemenete in der ListView
 */
public class SocialMediaListHandler {
    private static ListView<String> socialListView;

    public SocialMediaListHandler(ListView<String> givensocialListView) {
        socialListView = givensocialListView;
    }

    public static void displayMessage(final SocialMediaMessage mediaMessage) {
        if (socialListView == null) return;
        // In UI Thread einhaengen und Nachricht in ListView darstellen
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<String> items = socialListView.getItems();
                String message = mediaMessage.getMessage();
                items.add(message);
            }
        });
    }
}

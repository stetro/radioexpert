package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.messaging.messages.FacebookMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.SocialMediaMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.TwitterMessage;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Abboniert die Social Topics und stellt diese in der ListView dar
 * <p/>
 * User: Steffen Tröster
 * Date: 04.12.12
 * Time: 15:26
 * TODO: Bessere Darstellung der Social Media Elemenete in der ListView
 */
public class SocialMediaListHandler {
    private static ListView<SocialMediaMessage> socialListView;

    public SocialMediaListHandler(ListView<SocialMediaMessage> givensocialListView) {
        socialListView = givensocialListView;
        socialListView.setCellFactory(new Callback<ListView<SocialMediaMessage>, ListCell<SocialMediaMessage>>() {
            @Override
            public ListCell<SocialMediaMessage> call(ListView<SocialMediaMessage> stringListView) {
                return new SocialMediaCell();
            }
        });
    }

    public static void displayMessage(final SocialMediaMessage mediaMessage) {
        if (socialListView == null) return;
        // In UI Thread einhaengen und Nachricht in ListView darstellen
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<SocialMediaMessage> items = socialListView.getItems();
                items.add(mediaMessage);
            }
        });
    }

    private class SocialMediaCell extends ListCell<SocialMediaMessage> {
        public void updateItem(SocialMediaMessage item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                ImageView i = new ImageView();
                if (item instanceof TwitterMessage) {
                    i.setImage(new Image("gui/component/img/twitter.png"));
                } else if (item instanceof FacebookMessage) {
                    i.setImage(new Image("gui/component/img/facebook.png"));
                } else {
                    i.setImage(new Image("gui/component/img/email.png"));
                }
                HBox box = new HBox();
                box.setSpacing(10.0);
                box.getChildren().add(i);
                box.getChildren().add(new Text(this.getItem().getMessage()));
                setGraphic(box);

            }
        }
    }
}

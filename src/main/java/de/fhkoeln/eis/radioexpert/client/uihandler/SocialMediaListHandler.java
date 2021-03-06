package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.messaging.SocialMediaMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.FacebookMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.MailMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.TwitterMessage;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abboniert die Social Topics und stellt diese in der ListView dar
 * <p/>
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
        socialListView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (socialListView.getItems().size() == 0) return;

                Dragboard db = socialListView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(socialListView.getSelectionModel().getSelectedItem().getSender() + ": " +
                        socialListView.getSelectionModel().getSelectedItem().getMessage());
                db.setContent(content);
                mouseEvent.consume();
            }
        });
    }

    /**
     * Fügt eine Nachricht der Liste hinzu
     *
     * @param mediaMessage
     */
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

    /**
     * Löscht alle Nachrichten und
     */
    public static void clearMessages() {
        socialListView.getItems().clear();
        TwitterMessage message = new TwitterMessage("Interessant aber ich würde es wie folgt machen: ", "stetro", new Date());
        socialListView.getItems().add(message);
    }

    /**
     * UI Darstellungsklasse je nach Message Inhalt
     */
    private class SocialMediaCell extends ListCell<SocialMediaMessage> {
        public void updateItem(SocialMediaMessage item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                ImageView i = new ImageView();
                HBox box = new HBox();
                box.setSpacing(10.0);
                box.getChildren().add(i);
                if (item instanceof TwitterMessage) {
                    i.setImage(new Image("gui/component/img/twitter.png"));
                    VBox vbox = new VBox();
                    TwitterMessage tm = (TwitterMessage) this.getItem();
                    Text e = new Text("@" + tm.getUser());
                    e.setStyle("-fx-font-weight:bold;");
                    vbox.getChildren().add(e);
                    SimpleDateFormat format = new SimpleDateFormat("d.M.Y h:m:s");
                    Text date = new Text(format.format(tm.getTime()));
                    date.setStyle("-fx-font-weight:lighter;");
                    vbox.getChildren().add(date);
                    vbox.getChildren().add(new Text("@" + tm.getUser() + " : " + tm.getMessage()));
                    box.getChildren().add(vbox);
                } else if (item instanceof FacebookMessage) {
                    i.setImage(new Image("gui/component/img/facebook.png"));
                    box.getChildren().add(new Text(this.getItem().getMessage()));
                } else {
                    i.setImage(new Image("gui/component/img/mail.png"));
                    VBox vbox = new VBox();
                    MailMessage mm = (MailMessage) this.getItem();
                    Text sender = new Text(mm.getSender());
                    sender.setStyle("-fx-font-weight:bold;");
                    vbox.getChildren().add(sender);
                    SimpleDateFormat format = new SimpleDateFormat("d.m.Y h:m:s");
                    Text time = new Text(format.format(mm.getTimestamp()));
                    vbox.getChildren().add(time);
                    Text e = new Text(mm.getTitle());
                    e.setStyle("-fx-font-weight:bold;");
                    vbox.getChildren().add(e);
                    vbox.getChildren().add(new Text(mm.getMessage()));
                    box.getChildren().add(vbox);
                }
                setGraphic(box);

            }
        }
    }
}

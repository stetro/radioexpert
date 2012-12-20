package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.client.util.UserRole;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.InterviewElementMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import java.net.URL;

/**
 * TimeLine Funktionalität mit WebView Komponente
 * <p/>
 * User: Steffen Tröster
 * Date: 10.12.12
 * Time: 09:19
 */
public class TimeLineHandler {
    private static WebView timeLineWebView;

    public TimeLineHandler(WebView givenTimeLineWebView) {

        URL url = getClass().getResource("/gui/component/timeline.html");
        givenTimeLineWebView.getEngine().load(url.toExternalForm());

        timeLineWebView.setContextMenuEnabled(false);
        timeLineWebView = givenTimeLineWebView;
        timeLineWebView.getEngine().setJavaScriptEnabled(true);
        timeLineWebView.getEngine().setOnResized(new EventHandler<WebEvent<Rectangle2D>>() {
            @Override
            public void handle(WebEvent<Rectangle2D> rectangle2DWebEvent) {
                timeLineWebView.getEngine().executeScript("updateTimeLineSize()");
            }
        });
        buildAndBindContextMenu();
    }

    public static void setBroadcast(final BroadcastMessage broadcastMessage) {
        if (timeLineWebView == null) return;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(broadcastMessage.getEnd().getTime());
                timeLineWebView.getEngine().executeScript("updateTimeLine('" +
                        broadcastMessage.getTitle() + "','" +
                        broadcastMessage.getIntro() + "'," +
                        broadcastMessage.getStart().getTime() + "," +
                        broadcastMessage.getEnd().getTime() + ")");
            }
        });
    }

    public static void updateElement(final InterviewElementMessage object) {
        if (timeLineWebView == null) return;
        //var setModule = function(name, infotext, type, start, end)

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timeLineWebView.getEngine().executeScript("setModule('" + object.getTitle() + "','" + object.getInfo()
                        + "','interview'," + object.getStart().getTime() + "," + object.getEnd().getTime() + ")");
            }
        });
    }


    /**
     * Baut ein Kontextmenue auf (bei Rechtsklick auf Timeline):
     * Neuer Beitrag:
     * -> Audio anlegen
     * -> Interview anlegen
     * -> Moderation anlegen
     * -> Beitrag anlegen
     * <p/>
     * Nur fuer Redaktion !
     */
    private void buildAndBindContextMenu() {
        if (ClientApplication.role != UserRole.REDAKTEUR) return;

        final ContextMenu cm = new ContextMenu();
        Menu newElement = new Menu("Neuer Beitrag");
        MenuItem newSong = new MenuItem("Audio anlegen");
        MenuItem newInterview = new MenuItem("Interview anlegen");
        MenuItem newModeration = new MenuItem("Moderation anlegen");
        MenuItem newArticle = new MenuItem("Beitrag anlegen");
        newElement.getItems().addAll(newSong, newInterview, newModeration, newArticle);
        cm.getItems().add(newElement);
        timeLineWebView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (e.getButton() == MouseButton.SECONDARY)
                            cm.show(timeLineWebView, e.getScreenX(), e.getScreenY());
                        if (e.getButton() == MouseButton.PRIMARY)
                            cm.hide();
                    }
                });
        newSong.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MoreInformationHandler.createNewAudioDialog();
            }
        });
        newInterview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MoreInformationHandler.createNewInterviewDialog();
            }
        });
        newModeration.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MoreInformationHandler.createNewModerationDialog();
            }
        });
        newArticle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MoreInformationHandler.createNewArticleDialog();
            }
        });
    }

}

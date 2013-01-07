package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
import de.fhkoeln.eis.radioexpert.client.uihandler.jshandler.NewElementHandler;
import de.fhkoeln.eis.radioexpert.client.uihandler.jshandler.SelectElementHandler;
import de.fhkoeln.eis.radioexpert.client.util.UserRole;
import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastMessage;
import de.fhkoeln.eis.radioexpert.messaging.TimeLineElement;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * TimeLine Funktionalität mit WebView Komponente
 * <p/>
 * User: Steffen Tröster
 * Date: 10.12.12
 * Time: 09:19
 */
public class TimeLineHandler {
    private static WebView timeLineWebView;
    private static Logger logger = LoggerFactory.getLogger(TimeLineHandler.class);
    public static List<TimeLineElement> timeLineElements = new ArrayList<TimeLineElement>();

    public TimeLineHandler(WebView givenTimeLineWebView) {
        timeLineWebView = givenTimeLineWebView;
        timeLineWebView.getEngine().setJavaScriptEnabled(true);
        URL url = TimeLineHandler.class.getResource("/gui/component/timeline.html");
        timeLineWebView.getEngine().load(url.toExternalForm());
        timeLineWebView.setContextMenuEnabled(false);
        timeLineWebView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    JSObject jsObject = (JSObject) timeLineWebView.getEngine().executeScript("window");
                    jsObject.setMember("SelectElementHandler", new SelectElementHandler());
                }
            }
        });
        buildAndBindContextMenu();
    }

    public static void setBroadcast(final BroadcastMessage broadcastMessage) {
        if (timeLineWebView == null) return;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Script ausfuehren
                timeLineWebView.getEngine().executeScript("updateTimeLine('" +
                        broadcastMessage.getTitle() + "','" +
                        broadcastMessage.getIntro() + "'," +
                        broadcastMessage.getStart().getTime() + "," +
                        broadcastMessage.getEnd().getTime() + ")");
            }
        });
    }

    private static void displayAllElements() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timeLineWebView.getEngine().executeScript("$(\".module\").remove();");
            }
        });
        for (final TimeLineElement e : timeLineElements) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Javascript: function(name, infotext, type, start, end)
                    timeLineWebView.getEngine().executeScript("setModule('" + e.getTitle() + "','" + e.getInfo() + "','" + e.getType() + "'," + e.getStart().getTime() + "," + e.getEnd().getTime() + "," + e.getCreatedAt().getTime() + ")");
                }
            });
        }
    }

    public static void updateElement(final TimeLineElement object) {
        if (timeLineWebView == null) return;
        logger.info("Element wird aktualisiert oder hinzugefuegt");
        TimeLineElement tmp = null;
        for (TimeLineElement e : timeLineElements) {
            if (e.getCreatedAt().equals(object.getCreatedAt())) {
                tmp = e;
            }
        }
        if (tmp != null) {
            timeLineElements.remove(tmp);
        }
        timeLineElements.add(object);
        displayAllElements();
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
                        if (e.getButton() == MouseButton.SECONDARY && NewElementHandler.currentBroadcastMessage != null)
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

    public static void clearElements() {
        timeLineElements = new ArrayList<TimeLineElement>();
    }
}

package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.client.uihandler.jshandler.NewElementHandler;
import de.fhkoeln.eis.radioexpert.messaging.TimeLineElement;
import de.fhkoeln.eis.radioexpert.messaging.messages.AudioMessage;
import de.fhkoeln.eis.radioexpert.messaging.messages.InterviewMessage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Repraesentiert den Mittelteil der UI mit WebView Elementen
 * <p/>
 * User: Steffen Tröster
 * Date: 16.12.12
 * Time: 12:18
 */
public class MoreInformationHandler {
    private static WebView moreInformationWebView;
    private static Logger logger = LoggerFactory.getLogger(MoreInformationHandler.class);
    public static TimeLineElement currentSelectedElement;

    public MoreInformationHandler(WebView givenMoreInformationWebView) {
        moreInformationWebView = givenMoreInformationWebView;
        moreInformationWebView.setContextMenuEnabled(false);
        moreInformationWebView.getEngine().setJavaScriptEnabled(true);
        moreInformationWebView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    JSObject jsObject = (JSObject) moreInformationWebView.getEngine().executeScript("window");
                    jsObject.setMember("NewElementHandler", new NewElementHandler());
                }
            }
        });
        // DRAG AND DROP ->
        // Highlighting beim ueberfliegen
        moreInformationWebView.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (currentSelectedElement == null) return;
                if (dragEvent.getGestureSource() != moreInformationWebView) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            moreInformationWebView.getEngine().executeScript("highlightSocialMedia()");
                        }
                    });
                }
                dragEvent.consume();
            }
        });
        moreInformationWebView.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (currentSelectedElement == null) return;
                if (dragEvent.getGestureSource() != moreInformationWebView) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            moreInformationWebView.getEngine().executeScript("unHighlightSocialMedia()");
                        }
                    });
                }
                dragEvent.consume();
            }
        });
        // Copy Symbol bei korrekter Eigenschaft darstellen
        moreInformationWebView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (currentSelectedElement == null) return;
                dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
        });
        // Drag and Drop druchfuehren
        moreInformationWebView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (currentSelectedElement != null && dragEvent.getDragboard().hasString()) {
                    currentSelectedElement.addMessage(dragEvent.getDragboard().getString());
                    logger.info("SocialMediaElement " + dragEvent.getDragboard().getString());
                }
                dragEvent.setDropCompleted(true);
                dragEvent.consume();
            }
        });
    }

    public void showNewBroadcast() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        URL url = getClass().getResource("/gui/component/newBroadcast.html");
        moreInformationWebView.getEngine().load(url.toExternalForm());
        logger.info("Neue Sendung anlegen UI wird geladen ...");
    }

    public static void createNewAudioDialog() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        URL url = MoreInformationHandler.class.getResource("/gui/component/newAudio.html");
        moreInformationWebView.getEngine().load(url.toExternalForm());
        logger.info("Neues Audio anlegen UI wird geladen ...");
    }

    public static void createNewInterviewDialog() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        URL url = MoreInformationHandler.class.getResource("/gui/component/newAudio.html");
        moreInformationWebView.getEngine().load(url.toExternalForm());
        logger.info("Neues Audio anlegen UI wird geladen ...");
    }

    public static void createNewModerationDialog() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        System.out.println("new Moderation");
    }

    public static void createNewArticleDialog() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        System.out.println("new dialog");
    }

    public static void showElement(AudioMessage e) {
        URL url = MoreInformationHandler.class.getResource("/gui/component/audio.html");
        String resource = url.toExternalForm() + "?title=" + e.getInfo() + "&from=" + e.getStart().getTime() + "&to=" + e.getEnd().getTime();
        moreInformationWebView.getEngine().load(resource);
        currentSelectedElement = e;
        logger.info("Audio Anzeige UI wird geladen ...");
    }

    public static void showElement(InterviewMessage e) {
        currentSelectedElement = e;
    }
}


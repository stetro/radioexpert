package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;
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
import org.springframework.jms.core.JmsTemplate;

import java.net.URL;
import java.util.List;

/**
 * Repraesentiert den Mittelteil der UI mit WebView Elementen
 * <p/>
 * Date: 16.12.12
 * Time: 12:18
 */
public class MoreInformationHandler {
    private static WebView moreInformationWebView;
    private static Logger logger = LoggerFactory.getLogger(MoreInformationHandler.class);
    public static TimeLineElement currentSelectedElement;
    private JmsTemplate jmsTemplate;

    public MoreInformationHandler(WebView givenMoreInformationWebView) {
        jmsTemplate = ClientApplication.context.getBean(JmsTemplate.class);
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
            public void handle(final DragEvent dragEvent) {
                if (currentSelectedElement != null && dragEvent.getDragboard().hasString()) {
                    currentSelectedElement.addMessage(dragEvent.getDragboard().getString());
                    if (currentSelectedElement instanceof AudioMessage) {
                        jmsTemplate.convertAndSend("audio", currentSelectedElement);
                    } else if (currentSelectedElement instanceof InterviewMessage) {
                        jmsTemplate.convertAndSend("interview", currentSelectedElement);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            String script = "addMessage(\"" + dragEvent.getDragboard().getString().replace("\r\n", "<br/>").replace("\n", "<br/>") + "\")";
                            System.out.println(script);
                            moreInformationWebView.getEngine().executeScript(script);
                        }
                    });
                }
                dragEvent.setDropCompleted(true);
                dragEvent.consume();
            }
        });
    }

    /**
     * Stellt den Dialog zum erzeugen einer neuen Sendung dar
     */
    public void showNewBroadcast() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        URL url = getClass().getResource("/gui/component/newBroadcast.html");
        moreInformationWebView.getEngine().load(url.toExternalForm());
        logger.info("Neue Sendung anlegen UI wird geladen ...");
    }

    /**
     * Stellt den Dialog zum erzeugen eines neuen Audios dar
     */
    public static void createNewAudioDialog() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        URL url = MoreInformationHandler.class.getResource("/gui/component/newAudio.html");
        moreInformationWebView.getEngine().load(url.toExternalForm());
        logger.info("Neues Audio anlegen UI wird geladen ...");
    }

    /**
     * Stellt den Dialog zum erzeugen eines neuen Interviews dar
     */
    public static void createNewInterviewDialog() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        URL url = MoreInformationHandler.class.getResource("/gui/component/newInterview.html");
        moreInformationWebView.getEngine().load(url.toExternalForm());
        logger.info("Neues Interview anlegen UI wird geladen ...");
    }

    /**
     * Stellt den Dialog zum erzeugen einer neuen Moderation dar
     */
    public static void createNewModerationDialog() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        System.out.println("new Moderation");
    }

    /**
     * Stellt den Dialog zum erzeugen eines neuen Artikels dar
     */
    public static void createNewArticleDialog() {
        if (moreInformationWebView == null) return;

        currentSelectedElement = null;
        System.out.println("new dialog");
    }

    /**
     * Weitere Informationen über das selektierte Audio Element
     *
     * @param e
     */
    public static void showElement(AudioMessage e) {
        URL url = MoreInformationHandler.class.getResource("/gui/component/audio.html");
        String resource = url.toExternalForm() + "?title=" + e.getInfo() + "&from=" + e.getStart().getTime() + "&to=" + e.getEnd().getTime();
        appendSocialMediaMessages(e.getMessages(), resource);
        moreInformationWebView.getEngine().load(resource);
        currentSelectedElement = e;
        logger.info("Audio Anzeige UI wird geladen ...");
    }

    /**
     * Zeigt das selektierte Interview Element dar
     *
     * @param e
     */
    public static void showElement(InterviewMessage e) {
        URL url = MoreInformationHandler.class.getResource("/gui/component/interview.html");
        String resource = url.toExternalForm() + "?title=" + e.getInfo() + "&from=" + e.getStart().getTime() + "&to=" + e.getEnd().getTime() + "&thma=" + e.getTitle()
                + "&name=" + e.getName() + "&phone=" + e.getPhone() + "&mail=" + e.getMail() + "&street=" + e.getStreet() + "&city=" + e.getCity() + "&questions=" + e.getQuestions() + "&infotext=" + e.getInfo();
        appendSocialMediaMessages(e.getMessages(), resource);

        moreInformationWebView.getEngine().load(resource);
        currentSelectedElement = e;
        logger.info("Interview Anzeige UI wird geladen ...");
    }

    /**
     * Hinzufügen der Nachrichten beim Laden
     *
     * @param messages
     * @param resource
     */
    private static void appendSocialMediaMessages(List<String> messages, String resource) {
        int i = 0;
        for (String s : messages) {
            i++;
            resource += "&smsg[" + i + "]=" + s;
        }
    }
}


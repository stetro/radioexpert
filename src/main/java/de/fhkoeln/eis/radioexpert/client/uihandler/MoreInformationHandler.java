package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.client.uihandler.jshandler.NewBroadcastHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
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

    public MoreInformationHandler(WebView givenMoreInformationWebView) {
        moreInformationWebView = givenMoreInformationWebView;
        moreInformationWebView.setContextMenuEnabled(false);
        moreInformationWebView.getEngine().setJavaScriptEnabled(true);
    }

    public void showNewBroadcast() {
        moreInformationWebView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    JSObject jsObject = (JSObject) moreInformationWebView.getEngine().executeScript("window");
                    jsObject.setMember("NewBroadcastHandler", new NewBroadcastHandler());
                }
            }
        });
        URL url = getClass().getResource("/gui/component/newBroadcast.html");
        moreInformationWebView.getEngine().load(url.toExternalForm());
        logger.info("Neue Sendung anlegen UI wird geladen ...");
    }

    public static void createNewAudioDialog() {
        URL url = MoreInformationHandler.class.getResource("/gui/component/newAudio.html");
        moreInformationWebView.getEngine().load(url.toExternalForm());
        logger.info("Neues Audio anlegen UI wird geladen ...");
    }

    public static void createNewInterviewDialog() {
        System.out.println("new Interview");
    }

    public static void createNewModerationDialog() {
        System.out.println("new Moderation");
    }

    public static void createNewArticleDialog() {
        System.out.println("new dialog");
    }
}


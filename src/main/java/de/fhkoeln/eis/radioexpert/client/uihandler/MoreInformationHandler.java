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
    private final WebView moreInformationWebView;
    private Logger logger = LoggerFactory.getLogger(MoreInformationHandler.class);

    public MoreInformationHandler(WebView moreInformationWebView) {
        this.moreInformationWebView = moreInformationWebView;
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
}


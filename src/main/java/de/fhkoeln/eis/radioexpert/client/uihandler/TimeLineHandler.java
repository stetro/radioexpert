package de.fhkoeln.eis.radioexpert.client.uihandler;

import de.fhkoeln.eis.radioexpert.messaging.messages.BroadcastMessage;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
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
        timeLineWebView = givenTimeLineWebView;
        timeLineWebView.setContextMenuEnabled(false);
        loadLocalTimeLineHtmlComponent();
    }

    private void loadLocalTimeLineHtmlComponent() {
        timeLineWebView.getEngine().setJavaScriptEnabled(true);
        timeLineWebView.getEngine().setOnResized(new EventHandler<WebEvent<Rectangle2D>>() {
            @Override
            public void handle(WebEvent<Rectangle2D> rectangle2DWebEvent) {
                timeLineWebView.getEngine().executeScript("updateTimeLineSize()");
            }
        });
        URL url = getClass().getResource("/gui/component/timeline.html");
        timeLineWebView.getEngine().load(url.toExternalForm());
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

}

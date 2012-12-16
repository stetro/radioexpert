package de.fhkoeln.eis.radioexpert.client.uihandler;

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
    private WebView timeLineWebView;

    public TimeLineHandler(WebView timeLineWebView) {
        this.timeLineWebView = timeLineWebView;
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
}

package de.fhkoeln.eis.radioexpert.client.uihandler;

import javafx.scene.web.WebView;

import java.net.URL;

/**
 * User: Steffen Tröster
 * Date: 10.12.12
 * Time: 09:19
 * TimeLine Funktionalität mit WebView Komponente
 */
public class TimeLineHandler {
    private WebView timeLineWebView;

    public TimeLineHandler(WebView timeLineWebView) {
        this.timeLineWebView = timeLineWebView;
        loadLocalTimeLineHtmlComponent();
    }

    private void loadLocalTimeLineHtmlComponent() {
        timeLineWebView.getEngine().setJavaScriptEnabled(true);
        URL url = getClass().getResource("/gui/component/timeline.html");
        timeLineWebView.getEngine().load(url.toExternalForm());
    }
}

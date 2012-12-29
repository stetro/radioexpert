package de.fhkoeln.eis.radioexpert.client.util;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Informationsbox am oberen Rand. Für kurze Statusmeldungen.
 * <p/>
 * User: Steffen Tröster
 * Date: 29.12.12
 * Time: 09:54
 */
public class InfoBox {

    private static Text infoText;
    private static Rectangle infoRectangle;

    public static void showMessage(String message) {
        infoText.setText(message);
        Animation a = new Transition() {
            {
                setCycleDuration(Duration.seconds(1));
                setInterpolator(Interpolator.LINEAR);
            }

            @Override
            protected void interpolate(double v) {
                infoText.setTranslateY(v * 50);
                infoRectangle.setTranslateY(v * 50);
            }
        };
        a.play();
        a.setCycleCount(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                } finally {
                    hideMessage();
                }
            }
        }).start();
    }

    private static void hideMessage() {
        Animation a = new Transition() {
            {
                setCycleDuration(Duration.seconds(1));
                setInterpolator(Interpolator.LINEAR);
            }

            @Override
            protected void interpolate(double v) {
                infoText.setTranslateY(50 - v * 50);
                infoRectangle.setTranslateY(50 - v * 50);
            }
        };
        a.play();
        a.setCycleCount(1);
    }

    public static void setVariables(Text givenInfoText, Rectangle givenInfoRectangle) {
        infoText = givenInfoText;
        infoRectangle = givenInfoRectangle;
    }
}

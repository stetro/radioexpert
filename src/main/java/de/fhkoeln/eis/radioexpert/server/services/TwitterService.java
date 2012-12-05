package de.fhkoeln.eis.radioexpert.server.services;

import de.fhkoeln.eis.radioexpert.messaging.messages.TwitterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Twitter Service der in regelmaessiegen Abstaenden die TwitterAPI abfragt ob
 * nach dem bestimmten Suchkretaerium neue Tweets in der Timeline stehen
 *
 * @author Steffen Troester
 */

@Service
public class TwitterService implements RadioExpertService {
    /**
     * {@link org.springframework.jms.core.JmsTemplate} aus der applicationServerContext.xml (component-scan)
     */
    @Autowired
    JmsTemplate jmsTemplate;

    private final Logger logger = LoggerFactory.getLogger(TwitterService.class);
    /**
     * PollInteravl in ms angegeben
     */
    private int pollInterval = 20 * 1000;

    /**
     * Startet den Twitterserver
     */
    @Override
    public void start() {
        logger.info("Twitter Service startet ...");

        // Erstellt eine Factory Instanz um die Mentions
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("fZ53Ze82Ru1sGkO2cmlo1Q")
                .setOAuthConsumerSecret("7JGanZ5qRUIEDSTOHSUluAk7Qbb2VFbIFwUE8Vo")
                .setOAuthAccessToken("36389625-nXlCKDPuw1FuW90euDKxfOkY1g7zKoZEPU9o0KTeK")
                .setOAuthAccessTokenSecret("99tg2mQeVqlXeuhT9NN4KFi4U2ssC26rGGWjXWpx74Y");
        TwitterFactory tf = new TwitterFactory(cb.build());
        final Twitter twitter = tf.getInstance();


        // Polling Thread definieren der alle <pollInterval> ms pollt
        new Thread(new Runnable() {
            @Override
            public void run() {
                jmsTemplate.setPubSubDomain(true);
                while (true) {
                    try {
                        pollNewTweets(twitter);
                        Thread.sleep(pollInterval);
                    } catch (InterruptedException e) {
                        logger.info("TwitterService wurde beendet ...");
                        break;
                    } catch (TwitterException e) {
                        logger.error("Fehler beim laden der Mentions");
                        break;
                    }
                }
            }
        }).start();
    }

    /**
     * Fragt die neusten Tweets ab
     * TODO: Speicher letzte TweetID
     *
     * @param twitter
     * @throws TwitterException
     */
    private void pollNewTweets(Twitter twitter) throws TwitterException {
        List<Status> statuses = twitter.getMentions();
        for (Status status : statuses) {
            TwitterMessage twitterMessage = new TwitterMessage();
            twitterMessage.setMessage(status.getText());
            twitterMessage.setTime(status.getCreatedAt());
            twitterMessage.setUser(status.getUser().getName());
            jmsTemplate.convertAndSend("twitter", twitterMessage);
        }
    }
}

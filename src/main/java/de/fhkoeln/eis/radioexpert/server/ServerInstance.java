package de.fhkoeln.eis.radioexpert.server;

import de.fhkoeln.eis.radioexpert.server.services.FacebookService;
import de.fhkoeln.eis.radioexpert.server.services.MailService;
import de.fhkoeln.eis.radioexpert.server.services.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Haupt Server Instanz<br/>
 * Laedt den serverApplicationContext und die darin beinhaltetn EJB wie den
 * Persistenz Service, den Message Broker und die Application Services wie
 * Facebookanbindung und Twitteranbindung.
 *
 * @author Steffen Troester
 */
@Service
public class ServerInstance {

    private static final Logger logger = LoggerFactory.getLogger(ServerInstance.class);

    public static void main(String[] args) {
        try {
            logger.info("Server startet ...");
            // EJB's instanzieren
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationServerContext.xml");
            // Twitter Service starten
            TwitterService twitterService = (TwitterService) context.getBean("twitterService");
            //twitterService.start();
            // Facebook Service starten
            FacebookService facebookService = (FacebookService) context.getBean("facebookService");
            //facebookService.start();
            // MailService starten
            MailService mailService = (MailService) context.getBean("mailService");
            mailService.start();
            // TODO: Weitere Services starten !
            logger.info("Server Start erfolgreich ...");
        } catch (Exception e) {
            logger.error("Fehler beim Starten: ");
            logger.error(e.getMessage());
        }
    }
}

package de.fhkoeln.eis.radioexpert.server;

import de.fhkoeln.eis.radioexpert.server.services.RadioExpertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    private static List<RadioExpertService> services = new ArrayList<RadioExpertService>();

    private static final Logger logger = LoggerFactory.getLogger(ServerInstance.class);

    public static void main(String[] args) {
        try {
            logger.info("Server startet ...");
            // EJB's instanzieren
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationServerContext.xml");
            // Services aus Context laden

            services.add((RadioExpertService) context.getBean("twitterService"));
            // services.add((RadioExpertService)context.getBean("facebookService"));
            // services.add((RadioExpertService)context.getBean("mailService"));

            // Services starten
            for (RadioExpertService service : services) {
                service.start();
            }
            logger.info("Server Start erfolgreich ...");
        } catch (Exception e) {
            logger.error("Fehler beim Starten: ");
            logger.error(e.getMessage());
        }
    }
}

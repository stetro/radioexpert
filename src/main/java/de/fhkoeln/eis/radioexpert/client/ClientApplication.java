package de.fhkoeln.eis.radioexpert.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hauptklasse fuer die Clientseite mit GUI und ActiveMQ Anbindung
 * User: Steffen Tröster
 * Date: 21.11.12
 * Time: 19:08
 */
public class ClientApplication {

    public static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClientGUIController clientGUIController = (ClientGUIController) context.getBean("clientGUIController");
        clientGUIController.startClientGUI(args);
    }
}
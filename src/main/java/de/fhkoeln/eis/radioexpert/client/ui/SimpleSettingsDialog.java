package de.fhkoeln.eis.radioexpert.client.ui;

import de.fhkoeln.eis.radioexpert.client.ClientApplication;

import java.awt.*;
import java.util.Scanner;

import static java.util.Arrays.asList;

/**
 * Fragt zu Beginn jeder Sitzung den Server und die Nutzerrolle ab
 * User: Steffen Tröster
 * Date: 03.12.12
 * Time: 19:14
 */
public class SimpleSettingsDialog {

    public SimpleSettingsDialog() throws HeadlessException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Benutzerrolle (t/m/r):");
        String role = scanner.nextLine();
        if (!asList("t", "m", "r").contains(role)) {
            role = "r";
        }

        System.out.println("Benutzername :");
        String user = scanner.nextLine();

        System.out.println("Serveradresse :");
        String server = scanner.nextLine();
        if (server.isEmpty()) {
            server = "localhost";
        }

        ClientApplication.runApplication("localhost", user, role);
    }
}

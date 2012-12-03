package de.fhkoeln.eis.radioexpert.client;

import java.awt.*;
import java.util.Scanner;

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
        System.out.println("Benutzername :");
        String user = scanner.nextLine();
        System.out.println("Serveradresse :");
        String server = scanner.nextLine();
        ClientApplication.runApplication("localhost", "stetro", "r");
    }
}

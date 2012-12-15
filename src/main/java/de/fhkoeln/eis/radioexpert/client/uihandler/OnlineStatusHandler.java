package de.fhkoeln.eis.radioexpert.client.uihandler;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Verwaltet den OnlineStatus der Nutzer in der GUI
 * <p/>
 * User: Steffen Tröster
 * Date: 15.12.12
 * Time: 10:09
 */
@Service
public class OnlineStatusHandler {

    private static Map<String, Date> onlineStatus;
    private static final int seconds = 20;

    public OnlineStatusHandler() {
        onlineStatus = new HashMap<String, Date>();
    }

    public static void signPersonAsOnline(String user) {
        if (onlineStatus == null) return;
        onlineStatus.put(user, new Date());
    }

    public static void updateOnlineStatus() {
        if (onlineStatus == null) return;
        Date lastTime = new Date();
        lastTime.setTime(lastTime.getTime() - seconds * 1000);
        List<String> removeList = new ArrayList<String>();
        for (Map.Entry<String, Date> e : onlineStatus.entrySet()) {
            if (lastTime.after(e.getValue())) {
                removeList.add(e.getKey());
            }
        }
        for (String user : removeList) {
            onlineStatus.remove(user);
            //Offlinestatus hier deklarieren
        }
        for (Map.Entry<String, Date> e : onlineStatus.entrySet()) {
            //Onlinestatus hier deklarieren
        }
    }
}

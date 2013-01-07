package de.fhkoeln.eis.radioexpert.client.util;

/**
 * Repraesentiert die möglichen Nutzer Rollen
 * <p/>
 * User: Steffen Tröster
 * Date: 15.12.12
 * Time: 14:43
 */
public enum UserRole {

    REDAKTEUR("gui/component/img/redaktion.png", "r", "Redakteur"),
    MODERATOR("gui/component/img/moderation.png", "m", "Moderator"),
    TECHIKER("gui/component/img/technik.png", "t", "Techniker");


    private final String imagePath;
    private final String shortCut;
    private final String fullname;

    UserRole(String imagePath, String shortCut, String fullname) {
        this.imagePath = imagePath;
        this.shortCut = shortCut;
        this.fullname = fullname;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getShortCut() {
        return shortCut;
    }

    public String getFullname() {
        return fullname;
    }
}

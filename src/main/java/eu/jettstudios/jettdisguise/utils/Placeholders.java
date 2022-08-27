package eu.jettstudios.jettdisguise.utils;

public class Placeholders {

    public static String setPlaceholders(String s) {

        return s.replace("%prefix%", Config.PREFIX.getFormattedString());

    }

}

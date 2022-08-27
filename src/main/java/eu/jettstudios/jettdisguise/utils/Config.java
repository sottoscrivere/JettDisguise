package eu.jettstudios.jettdisguise.utils;

import eu.jettstudios.jettdisguise.JettDisguise;
import org.bukkit.configuration.file.FileConfiguration;

public enum Config {




    PREFIX("prefix"),
    PLAYER_ONLY("Messages.PLAYER_ONLY"),
    NO_PREMISSION("Messages.NO_PERMISSION"),
    ERROR("Messages.ERROR"),
    DISGUISED("Messages.DISGUISED"),
    UNDISGUISED("Messages.UNDISGUISED"),
    MODE("mode"),
    GUI_NAME("Gui.NAME"),
    UNSET_TYPE("Gui.NICK.unset.type"),
    UNSET_NAME("Gui.NICK.unset.name"),
    UNSET_DATA("Gui.NICK.unset.data"),
    UNSET_DURABILITY("Gui.NICK.unset.durability"),
    UNSET_UNBREAKABLE("Gui.NICK.unset.unbreakable"),
    UNSET_SET_DURABILITY("Gui.NICK.unset.set_durability"),
    SET_TYPE("Gui.NICK.set.type"),
    SET_NAME("Gui.NICK.set.name"),
    SET_DATA("Gui.NICK.set.data"),
    SET_DURABILITY("Gui.NICK.set.durability"),
    SET_UNBREAKABLE("Gui.NICK.set.unbreakable"),
    SET_SET_DURABILITY("Gui.NICK.set.set_durability"),
    LISTENING_FOR_NAME("Messages.LISTENING_FOR_NAME"),
    NICKNAME_SET("Messages.NICKNAME_SET"),
    NICKNAME_TOO_LONG("Messages.NICKNAME_TOO_LONG"),
    SKULL_TYPE("Gui.SKIN.type"),
    SKULL_NAME("Gui.SKIN.name"),
    SKULL_DATA("Gui.SKIN.data"),
    SKULL_DURABILITY("Gui.SKIN.durability"),
    SKULL_UNBREAKABLE("Gui.SKIN.unbreakable"),
    SKULL_SET_DURABILITY("Gui.SKIN.set_durability"),
    LISTENING_FOR_SKIN("Messages.LISTENING_FOR_SKIN"),
    SKIN_SET("Messages.SKIN_SET"),
    PLAYER_ONLINE("Messages.PLAYER_ONLINE");

    private FileConfiguration config = JettDisguise.getInstance().getConfig();

    private String path;

    private Config(String path) {

        this.path = path;

    }

    public String getString() {

        return config.getString(path);

    }

    public String getFormattedString() {

        return Color.color(getString());

    }

    public String getFormattedStringWithPlaceholders() {

        return Placeholders.setPlaceholders(Color.color(getString()));

    }

    public int getInt() {

        return config.getInt(path);


    }
    public double getDouble() {

        return config.getDouble(path);


    }

    public boolean getBoolean() {

        return config.getBoolean(path);

    }




}

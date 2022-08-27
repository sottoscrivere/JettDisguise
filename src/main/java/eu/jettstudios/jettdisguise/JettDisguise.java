package eu.jettstudios.jettdisguise;

import eu.jettstudios.jettdisguise.data.Data;
import eu.jettstudios.jettdisguise.listeners.ChatListener;
import eu.jettstudios.jettdisguise.listeners.InventoryListener;
import eu.jettstudios.jettdisguise.listeners.QuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class JettDisguise extends JavaPlugin {

    public static String getVersion() {

        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    }

    private static Data data;

    private static JettDisguise instance;

    public static JettDisguise getInstance() {
        return instance;
    }

    public JettDisguise() {

        instance = this;

    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        data = new Data();
        getCommand("disguise").setExecutor(new DisguiseCommand());
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(),this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Data getData() {
        return data;
    }
}

package eu.jettstudios.jettdisguise.data;

import eu.jettstudios.jettdisguise.DisguisedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class Data {

    private List<DisguisedPlayer> disguisedPlayerList;

    public List<DisguisedPlayer> getDisguisedPlayerList() {
        return disguisedPlayerList;
    }

    private Map<UUID, Inventory> listeningForName;

    public Map<UUID, Inventory> getListeningForName() {
        return listeningForName;
    }

    private Map<UUID, Inventory> listeningForSkin;

    public Map<UUID, Inventory> getListeningForSkin() {
        return listeningForSkin;
    }

    public DisguisedPlayer getDisguisedPlayer(Player player) {

        for (DisguisedPlayer disguisedPlayer : disguisedPlayerList) {

            if (player.getUniqueId().equals(disguisedPlayer.getPlayer().getUniqueId())) {

                return disguisedPlayer;

            }

        }
        return null;

    }

    public Data() {

        disguisedPlayerList = new ArrayList<>();
        listeningForName = new HashMap<>();
        listeningForSkin = new HashMap<>();

    }

}

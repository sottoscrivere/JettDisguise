package eu.jettstudios.jettdisguise;

import org.bukkit.entity.Player;

public class DisguisedPlayer {

    private String realNick = "";
    private String fakeNick = "";
    private Player player = null;

    public DisguisedPlayer(Player player, String fakeNick) {

        this.player = player;
        this.realNick = player.getName();
        this.fakeNick = fakeNick;

    }

    public String getRealNick() {
        return realNick;
    }

    public String getFakeNick() {
        return fakeNick;
    }

    public Player getPlayer() {
        return player;
    }
}

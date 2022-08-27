package eu.jettstudios.jettdisguise.listeners;

import eu.jettstudios.jettdisguise.DisguisedPlayer;
import eu.jettstudios.jettdisguise.JettDisguise;
import eu.jettstudios.jettdisguise.utils.Config;
import eu.jettstudios.jettdisguise.utils.Utils;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        if (Utils.isDisguised(e.getPlayer())) {
            Player player = e.getPlayer();
            if (JettDisguise.getData().getDisguisedPlayer(player) != null) {
                DisguisedPlayer disguisedPlayer = JettDisguise.getData().getDisguisedPlayer(player);
                if (Config.MODE.getInt() == 1) {
                    try {
                        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                        Object gameProfile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
                        Field nameField = gameProfile.getClass().getDeclaredField("name");
                        nameField.setAccessible(true);
                        nameField.set(gameProfile, disguisedPlayer.getRealNick());
                    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException ex) {
                        ex.printStackTrace();
                        player.sendMessage(Config.ERROR.getFormattedStringWithPlaceholders());
                    }
                }else if (Config.MODE.getInt() == 2) {
                    TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
                    TabAPI.getInstance().getTablistFormatManager().setName(tabPlayer, disguisedPlayer.getRealNick());
                    TabAPI.getInstance().getTeamManager().updateTeamData(tabPlayer);

                }
                JettDisguise.getData().getDisguisedPlayerList().remove(disguisedPlayer);
            }
        }

    }
}

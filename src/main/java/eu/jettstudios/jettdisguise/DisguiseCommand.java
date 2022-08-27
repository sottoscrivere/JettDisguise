package eu.jettstudios.jettdisguise;

import eu.jettstudios.jettdisguise.utils.Config;
import eu.jettstudios.jettdisguise.utils.Placeholders;
import eu.jettstudios.jettdisguise.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisguiseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("disguise")) {

            if (sender instanceof Player) {

                Player p = (Player) sender;

                if (p.hasPermission("jettdisguise.command.disguise")) {
                    JettDisguise.getData().getListeningForName().remove(p.getUniqueId());
                    JettDisguise.getData().getListeningForSkin().remove(p.getUniqueId());
                    if (!Utils.isDisguised(p)) {
                        Utils.openDisguiseGui(p);
                    }else {

                        Utils.undisguisePlayer(p);

                    }

                }else {

                    p.sendMessage(Config.NO_PREMISSION.getFormattedStringWithPlaceholders());

                }

            }else {

                sender.sendMessage(Config.PLAYER_ONLY.getFormattedStringWithPlaceholders());

            }

        }

        return false;
    }
}

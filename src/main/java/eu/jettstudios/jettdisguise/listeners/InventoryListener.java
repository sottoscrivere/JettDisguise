package eu.jettstudios.jettdisguise.listeners;

import eu.jettstudios.jettdisguise.JettDisguise;
import eu.jettstudios.jettdisguise.utils.Config;
import eu.jettstudios.jettdisguise.utils.Utils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getClickedInventory().getName().equals(Config.GUI_NAME.getFormattedString())) {

            if (e.getCurrentItem() == null) {

                return;

            }
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            Player p = (Player) e.getWhoClicked();
            if (clickedItem.hasItemMeta()) {

                if (clickedItem.getItemMeta().hasDisplayName()) {

                    if (clickedItem.getItemMeta().getDisplayName().equals(Config.UNSET_NAME.getFormattedString())) {

                        JettDisguise.getData().getListeningForName().put(p.getUniqueId(), p.getOpenInventory().getTopInventory());
                        p.closeInventory();
                        p.sendMessage(Config.LISTENING_FOR_NAME.getFormattedStringWithPlaceholders());

                    }
                    if (clickedItem.getItemMeta().getDisplayName().equals(Config.SET_NAME.getFormattedString())) {
                        String fakeNick = "Steve";
                        String fakeSkin = "Steve";
                        ItemStack i = clickedItem;
                        try {
                            Class craftItemStack = Class.forName("org.bukkit.craftbukkit." + JettDisguise.getVersion() + ".inventory.CraftItemStack");
                            Method asNmsStack = craftItemStack.getMethod("asNMSCopy", ItemStack.class);
                            Object nmsStack = asNmsStack.invoke(craftItemStack, i);
                            Object nbtTag = nmsStack.getClass().getMethod("getTag").invoke(nmsStack);
                            fakeNick = ((String)nbtTag.getClass().getMethod("getString", String.class).invoke(nbtTag, "fakeNick"));
                            i = e.getClickedInventory().getItem(24);
                            nmsStack = asNmsStack.invoke(craftItemStack, i);
                            nbtTag = nmsStack.getClass().getMethod("getTag").invoke(nmsStack);
                            fakeSkin = ((String)nbtTag.getClass().getMethod("getString", String.class).invoke(nbtTag, "fakeSkin"));
                        }catch(ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ex){
                            ex.printStackTrace();
                        }
                        if (fakeSkin == "Steve" || fakeSkin == "") {

                            fakeSkin = fakeNick;

                        }
                        Utils.disguisePlayer(p,fakeNick, fakeSkin);
                        p.closeInventory();
                    }
                    if (clickedItem.getItemMeta().getDisplayName().equals(Config.SKULL_NAME.getFormattedString())) {

                        JettDisguise.getData().getListeningForSkin().put(p.getUniqueId(), p.getOpenInventory().getTopInventory());
                        p.closeInventory();
                        p.sendMessage(Config.LISTENING_FOR_SKIN.getFormattedStringWithPlaceholders());

                    }
                }

            }

        }
    }


}

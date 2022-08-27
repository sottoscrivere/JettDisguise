package eu.jettstudios.jettdisguise.listeners;

import eu.jettstudios.jettdisguise.JettDisguise;
import eu.jettstudios.jettdisguise.utils.Config;
import eu.jettstudios.jettdisguise.utils.Utils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {

        if (JettDisguise.getData().getListeningForName().containsKey(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            String name = e.getMessage().split(" ")[0];
            if (name.length() <= 16) {
                if (Bukkit.getPlayerExact(name) == null) {
                    Player p = (Player) e.getPlayer();
                    Inventory inv = JettDisguise.getData().getListeningForName().get(p.getUniqueId());
                    ItemStack i = inv.getItem(20);
                    ItemMeta itemMeta = i.getItemMeta();
                    itemMeta.setDisplayName(Config.SET_NAME.getFormattedString());
                    i.setType(Material.valueOf(Config.SET_TYPE.getString()));
                    MaterialData materialData = i.getData();
                    if (Config.SET_SET_DURABILITY.getBoolean()) {

                        int maxuses = i.getType().getMaxDurability();
                        int durability = Config.SET_DURABILITY.getInt() - maxuses;
                        i.setDurability((short) durability);
                    }
                    i = new ItemStack(Material.valueOf(Config.SET_TYPE.getString()), 1, (short) Config.SET_DATA.getInt());
                    itemMeta.spigot().setUnbreakable(Config.SET_UNBREAKABLE.getBoolean());
                    materialData.setData((byte) Config.SET_DATA.getInt());
                    i.setData(materialData);
                    i.setItemMeta(itemMeta);
                    inv.setItem(20, i);
                    try {
                        Class craftItemStack = Class.forName("org.bukkit.craftbukkit." + JettDisguise.getVersion() + ".inventory.CraftItemStack");
                        Method asNmsStack = craftItemStack.getMethod("asNMSCopy", ItemStack.class);
                        Object nmsStack = asNmsStack.invoke(craftItemStack, i);
                        Class nbtTagClass = Class.forName("net.minecraft.server." + JettDisguise.getVersion() + ".NBTTagCompound");
                        Object nbtTag = nmsStack.getClass().getMethod("getTag").invoke(nmsStack);
                        nbtTag.getClass().getMethod("setString", String.class, String.class).invoke(nbtTag, "fakeNick", name);
                        nmsStack.getClass().getMethod("setTag", nbtTagClass).invoke(nmsStack, nbtTag);
                        Method asBukkitCopy = craftItemStack.getMethod("asBukkitCopy", Class.forName("net.minecraft.server." + JettDisguise.getVersion() + ".ItemStack"));
                        ItemStack is = (ItemStack) asBukkitCopy.invoke(craftItemStack, nmsStack);
                        inv.setItem(20, is);
                        //System.out.println(((String)nbtTag.getClass().getMethod("getString", String.class).invoke(nbtTag, "fakeNick")));


                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                        ex.printStackTrace();
                        p.sendMessage(Config.ERROR.getFormattedStringWithPlaceholders());
                    }
                    p.openInventory(inv);
                    JettDisguise.getData().getListeningForName().remove(p.getUniqueId());
                    p.sendMessage(Config.NICKNAME_SET.getFormattedStringWithPlaceholders().replaceAll("%nick%", name));

                }else {

                    e.getPlayer().sendMessage(Config.PLAYER_ONLINE.getFormattedStringWithPlaceholders());

                }
            }else {

                e.getPlayer().sendMessage(Config.NICKNAME_TOO_LONG.getFormattedStringWithPlaceholders().replaceAll("%nick%", name));

            }
        }
        if (JettDisguise.getData().getListeningForSkin().containsKey(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            String name = e.getMessage().split(" ")[0];
            Player p = (Player) e.getPlayer();
            Inventory inv = JettDisguise.getData().getListeningForSkin().get(p.getUniqueId());
            ItemStack i = inv.getItem(24);
            ItemMeta itemMeta = i.getItemMeta();
            itemMeta.setDisplayName(Config.SKULL_NAME.getFormattedString());
            i.setType(Material.valueOf(Config.SKULL_TYPE.getString()));
            MaterialData materialData = i.getData();
            if (Config.SET_SET_DURABILITY.getBoolean()) {

                int maxuses = i.getType().getMaxDurability();
                int durability = Config.SKULL_DURABILITY.getInt() - maxuses;
                i.setDurability((short) durability);
            }
            i = new ItemStack(Material.valueOf(Config.SKULL_TYPE.getString()), 1, (short) Config.SKULL_DATA.getInt());
            itemMeta.spigot().setUnbreakable(Config.SKULL_UNBREAKABLE.getBoolean());
            materialData.setData((byte) Config.SKULL_DATA.getInt());
            i.setData(materialData);
            i.setItemMeta(itemMeta);
            inv.setItem(24, i);
            try {
                Class craftItemStack = Class.forName("org.bukkit.craftbukkit." + JettDisguise.getVersion() + ".inventory.CraftItemStack");
                Method asNmsStack = craftItemStack.getMethod("asNMSCopy", ItemStack.class);
                Object nmsStack = asNmsStack.invoke(craftItemStack, i);
                Class nbtTagClass = Class.forName("net.minecraft.server." + JettDisguise.getVersion() + ".NBTTagCompound");
                Object nbtTag = nmsStack.getClass().getMethod("getTag").invoke(nmsStack);
                nbtTag.getClass().getMethod("setString", String.class, String.class).invoke(nbtTag, "fakeSkin", name);
                nmsStack.getClass().getMethod("setTag", nbtTagClass).invoke(nmsStack, nbtTag);
                Method asBukkitCopy = craftItemStack.getMethod("asBukkitCopy", Class.forName("net.minecraft.server." + JettDisguise.getVersion() + ".ItemStack"));
                ItemStack is = (ItemStack) asBukkitCopy.invoke(craftItemStack, nmsStack);
                inv.setItem(24, is);
                //System.out.println(((String)nbtTag.getClass().getMethod("getString", String.class).invoke(nbtTag, "fakeNick")));


            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                ex.printStackTrace();
                p.sendMessage(Config.ERROR.getFormattedStringWithPlaceholders());
            }
            p.openInventory(inv);
            JettDisguise.getData().getListeningForSkin().remove(p.getUniqueId());
            p.sendMessage(Config.SKIN_SET.getFormattedStringWithPlaceholders().replaceAll("%nick%", name));


        }

    }
}

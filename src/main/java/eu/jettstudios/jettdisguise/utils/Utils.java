package eu.jettstudios.jettdisguise.utils;

import eu.jettstudios.jettdisguise.DisguisedPlayer;
import eu.jettstudios.jettdisguise.JettDisguise;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Utils {

    public static void disguisePlayer(Player player, String fakeNick, String skinName) {

        JettDisguise.getData().getDisguisedPlayerList().add(new DisguisedPlayer(player, fakeNick));
        if (Config.MODE.getInt() == 1) {
            try {
                Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                Object gameProfile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
                Field nameField = gameProfile.getClass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(gameProfile, fakeNick);
                for (Player players : Bukkit.getOnlinePlayers()) {

                    players.hidePlayer(player);
                    players.showPlayer(player);

                }
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException e) {
                e.printStackTrace();
                player.sendMessage(Config.ERROR.getFormattedStringWithPlaceholders());
            }
        } else if (Config.MODE.getInt() == 2) {
            TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
            TabAPI.getInstance().getTablistFormatManager().setName(tabPlayer, fakeNick);
            TabAPI.getInstance().getTeamManager().forceTeamName(tabPlayer, "z");


        }
        SkinsRestorerAPI skinsRestorerAPI = SkinsRestorerAPI.getApi();
        try {
            skinsRestorerAPI.applySkin(new PlayerWrapper(player), skinName);
        } catch (SkinRequestException e) {}
        player.sendMessage(Config.DISGUISED.getFormattedStringWithPlaceholders());
    }

    public static void undisguisePlayer(Player player) {
        if (JettDisguise.getData().getDisguisedPlayer(player) != null) {
            DisguisedPlayer disguisedPlayer = JettDisguise.getData().getDisguisedPlayer(player);
            if (Config.MODE.getInt() == 1) {
                    try {
                        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                        Object gameProfile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
                        Field nameField = gameProfile.getClass().getDeclaredField("name");
                        nameField.setAccessible(true);
                        nameField.set(gameProfile, disguisedPlayer.getRealNick());
                        for (Player players : Bukkit.getOnlinePlayers()) {

                            players.hidePlayer(player);
                            players.showPlayer(player);

                        }
                    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException e) {
                        e.printStackTrace();
                        player.sendMessage(Config.ERROR.getFormattedStringWithPlaceholders());
                    }
            }else if (Config.MODE.getInt() == 2) {
                TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
                TabAPI.getInstance().getTablistFormatManager().setName(tabPlayer, disguisedPlayer.getRealNick());
                TabAPI.getInstance().getTeamManager().updateTeamData(tabPlayer);

            }
            JettDisguise.getData().getDisguisedPlayerList().remove(disguisedPlayer);
            player.sendMessage(Config.UNDISGUISED.getFormattedStringWithPlaceholders());
            SkinsRestorerAPI skinsRestorerAPI = SkinsRestorerAPI.getApi();
            try {
                skinsRestorerAPI.applySkin(new PlayerWrapper(player), disguisedPlayer.getRealNick());
            } catch (SkinRequestException e) {}
        }
    }

    public static boolean isDisguised(Player player) {

        return (JettDisguise.getData().getDisguisedPlayer(player) != null);

    }

    public static Inventory getDisguiseGui() {

        Inventory inv = Bukkit.getServer().createInventory(null, 45, Config.GUI_NAME.getFormattedString());
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (int i = 0; i<45; i++) {

            inv.setItem(i, itemStack);

        }
        itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        for (int i = 0; i < 9; i++) {

            inv.setItem(i, itemStack);

        }

        for (int i = 36; i<45; i++) {

            inv.setItem(i, itemStack);

        }
        itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
        inv.setItem(9, itemStack);
        inv.setItem(18, itemStack);
        inv.setItem(27, itemStack);
        inv.setItem(17, itemStack);
        inv.setItem(26, itemStack);
        inv.setItem(35, itemStack);
        itemStack = new ItemStack(Material.valueOf(Config.UNSET_TYPE.getString()), 1, (short) Config.UNSET_DATA.getInt());
        int maxuses = itemStack.getType().getMaxDurability();
        int durability = maxuses - Config.UNSET_DURABILITY.getInt();
        itemMeta.setDisplayName(Config.UNSET_NAME.getFormattedString());
        itemMeta.spigot().setUnbreakable(Config.UNSET_UNBREAKABLE.getBoolean());
        itemStack.setItemMeta(itemMeta);
        if (Config.UNSET_SET_DURABILITY.getBoolean()) {
            itemStack.setDurability((short) durability);
            MaterialData materialData = itemStack.getData();
            materialData.setData((byte) Config.UNSET_DATA.getInt());
            itemStack.setData(materialData);
        }
        inv.setItem(20, itemStack);
        itemStack = new ItemStack(Material.valueOf(Config.SKULL_TYPE.getString()), 1, (short) Config.SKULL_DATA.getInt());
        maxuses = itemStack.getType().getMaxDurability();
        durability = maxuses - Config.SKULL_DURABILITY.getInt();
        itemMeta.setDisplayName(Config.SKULL_NAME.getFormattedString());
        itemMeta.spigot().setUnbreakable(Config.SKULL_UNBREAKABLE.getBoolean() );
        itemStack.setItemMeta(itemMeta);
        if (Config.SKULL_SET_DURABILITY.getBoolean()) {
            itemStack.setDurability((short) durability);
            MaterialData materialData = itemStack.getData();
            materialData.setData((byte) Config.SKULL_DATA.getInt());
            itemStack.setData(materialData);
        }
        inv.setItem(24, itemStack);
        return inv;

    }

    public static void openDisguiseGui(Player player) {

        Inventory inv = Bukkit.getServer().createInventory(null, 45, Config.GUI_NAME.getFormattedString());
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (int i = 0; i<45; i++) {

            inv.setItem(i, itemStack);

        }
        itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        for (int i = 0; i < 9; i++) {

            inv.setItem(i, itemStack);

        }

        for (int i = 36; i<45; i++) {

            inv.setItem(i, itemStack);

        }
        itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
        inv.setItem(9, itemStack);
        inv.setItem(18, itemStack);
        inv.setItem(27, itemStack);
        inv.setItem(17, itemStack);
        inv.setItem(26, itemStack);
        inv.setItem(35, itemStack);
        itemStack = new ItemStack(Material.valueOf(Config.UNSET_TYPE.getString()), 1, (short) Config.UNSET_DATA.getInt());
        int maxuses = itemStack.getType().getMaxDurability();
        int durability = maxuses - Config.UNSET_DURABILITY.getInt();
        itemMeta.setDisplayName(Config.UNSET_NAME.getFormattedString());
        itemStack.setItemMeta(itemMeta);
        if (Config.UNSET_SET_DURABILITY.getBoolean()) {
            itemStack.setDurability((short) durability);
            MaterialData materialData = itemStack.getData();
            materialData.setData((byte) Config.UNSET_DATA.getInt());
            itemStack.setData(materialData);
        }
        inv.setItem(20, itemStack);
        itemStack = new ItemStack(Material.valueOf(Config.SKULL_TYPE.getString()), 1, (short) Config.SKULL_DATA.getInt());
        maxuses = itemStack.getType().getMaxDurability();
        durability = maxuses - Config.SKULL_DURABILITY.getInt();
        itemMeta.setDisplayName(Config.SKULL_NAME.getFormattedString());
        itemMeta.spigot().setUnbreakable(Config.SKULL_UNBREAKABLE.getBoolean() );
        itemStack.setItemMeta(itemMeta);
        if (Config.SKULL_SET_DURABILITY.getBoolean()) {
            itemStack.setDurability((short) durability);
            MaterialData materialData = itemStack.getData();
            materialData.setData((byte) Config.SKULL_DATA.getInt());
            itemStack.setData(materialData);
        }
        inv.setItem(24, itemStack);
        player.openInventory(inv);
    }

}

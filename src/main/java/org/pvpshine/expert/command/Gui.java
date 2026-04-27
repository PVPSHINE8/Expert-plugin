package org.pvpshine.expert.command;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
//import net.minecraft.world.item.component.ResolvableProfile;
import java.lang.reflect.Field;
import java.util.UUID;


public class Gui {

    public enum CustomHead{
        STATSBTN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDAwY2ZkZjA2YzY4MTdmZmY4YTdjZjdhNGJlYWI4NjFhZGMwMzAxZDQ4MGQ3YjBkNDBjNjlkMDUzMWIwMWZmOSJ9fX0="),
        RED("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGRhZGU5ZTYyNjM2NzE3NWRkYmZmZDI5NjU1ZWQzMjkzZjM3MzM4YmRkZDU2MmQ3OWE3N2VhMzRkMTE2MTUifX19"),
        CYAN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODk5ZDczZWFiYWQzMWI4MzRkZTEyOTFiODA1MjVhOGQxNDdmOGMyZGY0ZDhlNWJmZTNiMTEyZDY2YWU2MDczIn19fQ=="),
        GREEN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTliNzEwN2E2YTJiM2NhNDVlZjI2ZDI4Y2Q2ODVkNzMyODNkMTI0N2VjYmQwMTc0YmNkY2VkY2RkYmNjNzc5In19fQ");
        private final String base64;
        CustomHead(String base64){
            this.base64 = base64;

        }

        public String getBase64() {
            return base64;
        }
    }

    public static ItemStack getHeadFromBase64(String base64, String displayName){

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "QEventBoxHead");
        profile.getProperties().put("textures", new Property("textures", base64));
        try {
            Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            var ctor = fieldType.getDeclaredConstructor(GameProfile.class);
            ctor.setAccessible(true);
            field.set(meta, ctor.newInstance(profile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        meta.setDisplayName(displayName);
        skull.setItemMeta(meta);
        return skull;

    }
    public static void OpenGui(org.bukkit.entity.Player player){
        Inventory inv = Bukkit.createInventory(null, 27, "ExpertGUI");

        inv.setItem(8, getHeadFromBase64(CustomHead.STATSBTN.getBase64(), ChatColor.GRAY + "ѕᴛᴀᴛѕ"));
        inv.setItem(11, getHeadFromBase64(CustomHead.RED.getBase64(), ChatColor.DARK_RED + "Red"));
        inv.setItem(13, getHeadFromBase64(CustomHead.CYAN.getBase64(), ChatColor.BLUE + "Cyan"));
        inv.setItem(15, getHeadFromBase64(CustomHead.GREEN.getBase64(), ChatColor.GREEN + "Green"));
        player.openInventory(inv);

    }



}

package org.pvpshine.expert.command;
import com.mojang.authlib.GameProfile;
import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.pvpshine.expert.Expert;
import org.pvpshine.expert.listener.GuiListener;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import org.bukkit.ChatColor;
public class ExpertCommand implements CommandExecutor {

    private final AuraSkillsApi auraApi = AuraSkillsApi.get();
    private final Expert plugin;
    private HeadDatabaseAPI hdb;
    public ExpertCommand(Expert plugin) {
        this.plugin = plugin;

    }
    private GameProfile makeProfileFromBase64(String base64) {
        if (base64 == null || base64.isEmpty()) return null;
        try {
            GameProfile profile = new GameProfile(UUID.randomUUID(), "QEventBoxHead");
            profile.getProperties().put("textures", new Property("textures", base64));
            return profile;
        } catch (Throwable t) {
            return null;
        }
    }


    @Override

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //if player use command, open the chest gui
        HeadDatabaseAPI api = new HeadDatabaseAPI();

        if (command.getName().equalsIgnoreCase("expert")) {
            Player p = (Player) sender;
            Gui.OpenGui(p);
        }

        if (command.getName().equalsIgnoreCase("expertreset")) {
            Player p = (Player) sender;
            UUID id = p.getUniqueId();
            SkillsUser user = auraApi.getUser(id);
            if (!(sender instanceof Player)) return true; //kalo yg command player, bakal jalan


            if (args.length < 1) {
                p.sendMessage("Gunakan: /expertreset <player>"); // kalo kata cuma 1 kata
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]); //bikin player yang harus di targetin siapa di bagian kata kedua, atau setelah command

            if (target == null) {
                p.sendMessage("Player tidak ditemukan"); //kalo nickname player ga ketemu
                return true;
            }

    //hapus semua hash
            GuiListener.sudahClickRed.remove(target.getUniqueId());
            GuiListener.sudahClickGreen.remove(target.getUniqueId());
            GuiListener.sudahClickCyan.remove(target.getUniqueId());

            GuiListener.lockRed.remove(target.getUniqueId());
            GuiListener.lockGreen.remove(target.getUniqueId());
            GuiListener.lockCyan.remove(target.getUniqueId());

    //hapus semua modifier
            user.removeStatModifier("strength");
            user.removeStatModifier("cdamage");
            user.removeStatModifier("health");
            user.removeStatModifier("toughness");
            user.removeStatModifier("speed");

            user.removeStatModifier("strengthg");
            user.removeStatModifier("cdamageg");
            user.removeStatModifier("healthg");
            user.removeStatModifier("toughnessg");
            user.removeStatModifier("speedg");
            p.sendMessage("Berhasil reset class " + target.getName());
            return true;
        }

        return true;

    }

}





package org.pvpshine.expert;
import org.bukkit.plugin.java.JavaPlugin;
import dev.aurelium.auraskills.api.stat.Stats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.pvpshine.expert.command.ExpertCommand;
import org.pvpshine.expert.listener.GuiListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public final class Expert extends JavaPlugin implements Listener, CommandExecutor {
    @Override
    public void onEnable() {
        // Register Listener
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);

        // Register Command
        getCommand("expert").setExecutor(new ExpertCommand(this));
        getCommand("expertreset").setExecutor(new ExpertCommand(this)); // Tambahkan ini
    }
}
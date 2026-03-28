package org.pvpshine.expert;

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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public final class Expert extends JavaPlugin implements Listener, CommandExecutor {
    public Set<UUID> sudahPilih = new HashSet<>();
    @Override
    public void onEnable() {
        getCommand("expert").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);

    }



}
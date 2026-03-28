package org.pvpshine.expert.listener;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.stat.StatModifier;
import dev.aurelium.auraskills.api.stat.Stats;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.Material;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.pvpshine.expert.Expert;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GuiListener implements Listener {

    public static Set<UUID> sudahClickRed = new HashSet<>();
    public static Set<UUID> sudahClickGreen = new HashSet<>();
    public static Set<UUID> sudahClickCyan = new HashSet<>();

    public static Set<UUID> lockRed = new HashSet<>();
    public static Set<UUID> lockGreen = new HashSet<>();
    public static Set<UUID> lockCyan = new HashSet<>();

    private final AuraSkillsApi api = AuraSkillsApi.get();
    private final Expert plugin;

    public GuiListener(Expert plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("Menu Expert")) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;

        Player p = (Player) e.getWhoClicked();
        Inventory gui = e.getInventory();

        int[] slots = {11, 13, 15};

        for (int slot : slots) {
            ItemStack i = gui.getItem(slot);
            if (i == null) continue;

            ItemMeta m = i.getItemMeta();
            m.removeEnchant(Enchantment.UNBREAKING);
            i.setItemMeta(m);
        }

        ItemStack clicked = e.getCurrentItem();
        ItemMeta meta = clicked.getItemMeta();
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        clicked.setItemMeta(meta);

        if (clicked.getType() == Material.RED_WOOL) {
            ClickRed(p);
        }

        if (clicked.getType() == Material.CYAN_WOOL) {
            ClickCyan(p);
        }

        if (clicked.getType() == Material.GREEN_WOOL) {
            ClickGreen(p);
        }
    }

    public void ClickRed(Player player) {
        UUID id = player.getUniqueId();

        if (sudahClickRed.contains(id)) {
            player.sendMessage("Kamu telah memilih Strong");
            return;
        }
        if (lockGreen.contains(id)) {
            player.sendMessage("Kamu telah memilih Tank");
            return;
        }
        if (lockCyan.contains(id)) {
            player.sendMessage("Kamu telah memilih Neutral");
            return;
        }

        SkillsUser user = api.getUser(id);

        user.removeStatModifier("strengthg");
        user.removeStatModifier("cdamageg");
        user.removeStatModifier("healthg");
        user.removeStatModifier("toughnessg");
        user.removeStatModifier("speedg");

        user.addStatModifier(new StatModifier("strength", Stats.STRENGTH, 35.0));
        user.addStatModifier(new StatModifier("cdamage", Stats.CRIT_DAMAGE, 15.0));
        user.addStatModifier(new StatModifier("health", Stats.HEALTH, -20.0));
        user.addStatModifier(new StatModifier("toughness", Stats.TOUGHNESS, -15.0));
        user.addStatModifier(new StatModifier("speed", Stats.SPEED, 10.0));

        sudahClickRed.add(id);
        sudahClickGreen.remove(id);
        sudahClickCyan.remove(id);

        lockRed.add(id);
        lockGreen.remove(id);
        lockCyan.remove(id);

        spawnFirework(player, Color.RED);
        player.sendMessage("Kamu menjadi Strong");
    }

    public void ClickCyan(Player player) {
        UUID id = player.getUniqueId();

        if (sudahClickCyan.contains(id)) {
            player.sendMessage("Kamu telah memilih Neutral");
            return;
        }
        if (lockGreen.contains(id)) {
            player.sendMessage("Kamu telah memilih Tank");
            return;
        }
        if (lockRed.contains(id)) {
            player.sendMessage("Kamu telah memilih Strong");
            return;
        }

        SkillsUser user = api.getUser(id);

        user.removeStatModifier("strength");
        user.removeStatModifier("cdamage");
        user.removeStatModifier("cchance");
        user.removeStatModifier("health");
        user.removeStatModifier("speed");

        user.removeStatModifier("strengthg");
        user.removeStatModifier("cdamageg");
        user.removeStatModifier("cchanceg");
        user.removeStatModifier("healthg");
        user.removeStatModifier("speedg");

        sudahClickCyan.add(id);
        sudahClickRed.remove(id);
        sudahClickGreen.remove(id);

        lockCyan.add(id);
        lockRed.remove(id);
        lockGreen.remove(id);

        spawnFirework(player, Color.AQUA);
        player.sendMessage("Kamu memilih Neutral");
    }

    public void ClickGreen(Player player) {
        UUID id = player.getUniqueId();

        if (sudahClickGreen.contains(id)) {
            player.sendMessage("Kamu telah memilih Tank");
            return;
        }
        if (lockCyan.contains(id)) {
            player.sendMessage("Kamu telah memilih Neutral");
            return;
        }
        if (lockRed.contains(id)) {
            player.sendMessage("Kamu telah memilih Strong");
            return;
        }

        SkillsUser user = api.getUser(id);

        user.removeStatModifier("strength");
        user.removeStatModifier("cdamage");
        user.removeStatModifier("cchance");
        user.removeStatModifier("health");
        user.removeStatModifier("toughness");
        user.removeStatModifier("speed");

        user.addStatModifier(new StatModifier("strengthg", Stats.STRENGTH, -15.0));
        user.addStatModifier(new StatModifier("cdamageg", Stats.CRIT_DAMAGE, -10.0));
        user.addStatModifier(new StatModifier("healthg", Stats.HEALTH, 45.0));
        user.addStatModifier(new StatModifier("toughnessg", Stats.TOUGHNESS, 35.0));
        user.addStatModifier(new StatModifier("speedg", Stats.SPEED, -20.0));

        sudahClickGreen.add(id);
        sudahClickRed.remove(id);
        sudahClickCyan.remove(id);

        lockGreen.add(id);
        lockRed.remove(id);
        lockCyan.remove(id);

        spawnFirework(player, Color.GREEN);
        player.sendMessage("Kamu menjadi Tank");
    }

    public void spawnFirework(Player p, Color color) {
        Firework fw = p.getWorld().spawn(p.getLocation(), Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();

        meta.addEffect(FireworkEffect.builder()
                .withColor(color)
                .with(FireworkEffect.Type.BALL_LARGE)
                .flicker(true)
                .build());

        meta.setPower(1);
        fw.setFireworkMeta(meta);
        fw.detonate();
    }
}
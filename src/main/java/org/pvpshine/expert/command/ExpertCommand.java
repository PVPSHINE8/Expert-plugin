package org.pvpshine.expert.command;
import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.pvpshine.expert.Expert;
import org.pvpshine.expert.listener.GuiListener;

import java.util.*;

import org.bukkit.ChatColor;
public class ExpertCommand implements CommandExecutor {

    private final AuraSkillsApi api = AuraSkillsApi.get();
    private final Expert plugin;

    public ExpertCommand(Expert plugin) {
        this.plugin = plugin;

    }

    @Override

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //if player use command, open the chest gui


        if (command.getName().equalsIgnoreCase("expert")) {


            Player p = (Player) sender;
            Inventory gui = Bukkit.createInventory(null, 27, "Menu Expert");
            p.openInventory(gui);
            //red
            ItemStack red = new ItemStack(Material.RED_WOOL);
            ItemMeta redMeta = red.getItemMeta();
            redMeta.setDisplayName(ChatColor.DARK_RED + "ѕᴛʀᴏɴɢ");
            redMeta.setLore(Arrays.asList(
                    "",
                    "§f[ѕᴛᴀᴛѕ]",
                    "§cᴅᴀᴍᴀɢᴇ        +35",
                    "§cᴄʀɪᴛ-ᴅᴀᴍᴀɢᴇ   +15",
                    "§cѕᴘᴇᴇᴅ         +10",
                    "§cʜᴇᴀʟᴛʜ        -20",
                    "§cᴛᴏᴜɢʜᴛɴᴇѕѕ    -15"
            ));
            red.setItemMeta(redMeta);
            //cyan
            ItemStack cyan = new ItemStack(Material.CYAN_WOOL);
            ItemMeta cyanMeta = cyan.getItemMeta();
            cyanMeta.setDisplayName(ChatColor.WHITE + "ɴᴇᴜᴛʀᴀʟ");
            cyanMeta.setLore(Arrays.asList(
                    "",
                    "§f[ѕᴛᴀᴛѕ ɴᴇᴜᴛʀᴀʟ]"
            ));
            cyan.setItemMeta(cyanMeta);

            //green
            ItemStack green = new ItemStack(Material.GREEN_WOOL);
            ItemMeta greenMeta = green.getItemMeta();
            greenMeta.setDisplayName(ChatColor.DARK_GREEN + "ᴛᴀɴᴋ");
            greenMeta.setLore(Arrays.asList(
                    "",
                    "§f[ѕᴛᴀᴛѕ]",
                    "§aᴅᴀᴍᴀɢᴇ        -15",
                    "§aᴄʀɪᴛ-ᴅᴀᴍᴀɢᴇ   -10",
                    "§aѕᴘᴇᴇᴅ         -20",
                    "§aʜᴇᴀʟᴛʜ        +45",
                    "§aᴛᴏᴜɢʜᴛɴᴇѕѕ    +35"
            ));
            green.setItemMeta(greenMeta);


            gui.setItem(11, red);
            gui.setItem(13, cyan);
            gui.setItem(15, green);

            UUID id = p.getUniqueId();

            if (GuiListener.sudahClickRed.contains(id)) {
                ItemMeta m = red.getItemMeta();
                m.addEnchant(Enchantment.UNBREAKING, 1, true);
                m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                red.setItemMeta(m);
            }

            if (GuiListener.sudahClickCyan.contains(id)) {
                ItemMeta m = cyan.getItemMeta();
                m.addEnchant(Enchantment.UNBREAKING, 1, true);
                m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cyan.setItemMeta(m);
            }

            if (GuiListener.sudahClickGreen.contains(id)) {
                ItemMeta m = green.getItemMeta();
                m.addEnchant(Enchantment.UNBREAKING, 1, true);
                m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                green.setItemMeta(m);
            }

        }
        if (command.getName().equalsIgnoreCase("expertreset")) {
            Player p = (Player) sender;
            UUID id = p.getUniqueId();
            SkillsUser user = api.getUser(id);
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





package me.waldxn.adminutils.cmds;

import me.waldxn.adminutils.utils.ConfigCreator;
import me.waldxn.adminutils.utils.InventoriesConfig;
import me.waldxn.adminutils.utils.PlayerDataConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;

public class Freeze implements CommandExecutor, Listener {

    private ConfigCreator cc;
    private PlayerDataConfig playerdata;
    private InventoriesConfig inventories;

    private ItemStack frz, unfrz, frzall, unfrzall, exit;
    private ItemMeta frzm, unfrzm, frzallm, unfrzallm, exitm;

    public Freeze(PlayerDataConfig pdc, ConfigCreator creator, InventoriesConfig ic) {
        this.cc = creator;
        this.playerdata = pdc;
        this.inventories = ic;

        frz = new ItemStack(Material.WOOL, 1, (short) 3);
        unfrz = new ItemStack(Material.WOOL, 1, (short) 3);
        frzall = new ItemStack(Material.WOOL, 1);
        unfrzall = new ItemStack(Material.WOOL, 1);
        exit = new ItemStack(Material.BED, 1);

        frzm = frz.getItemMeta();
        unfrzm = unfrz.getItemMeta();
        frzallm = frzall.getItemMeta();
        unfrzallm = unfrzall.getItemMeta();
        exitm = exit.getItemMeta();

        frzm.setDisplayName(ChatColor.AQUA + "Freeze a player");
        frz.setItemMeta(frzm);
        unfrzm.setDisplayName(ChatColor.AQUA + "Unfreeze a player");
        unfrz.setItemMeta(unfrzm);
        frzallm.setDisplayName(ChatColor.AQUA + "Freeze the entire server");
        frzall.setItemMeta(frzallm);
        unfrzallm.setDisplayName(ChatColor.AQUA + "Unfreeze the entire server");
        unfrzall.setItemMeta(unfrzallm);
        exitm.setDisplayName(ChatColor.GOLD + "Close Menu");
        exit.setItemMeta(exitm);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (sender.hasPermission("utils.freeze")) {

            if (args.length == 0 && sender instanceof Player) {
                Player player = (Player) sender;
                Inventory freeze = Bukkit.createInventory(player, 9,
                        ChatColor.translateAlternateColorCodes('&', inventories.getInventoriesString("Inventories.Freeze.Title")));
                player.openInventory(freeze);
                freeze.setItem(0, frz);
                freeze.setItem(3, unfrz);
                freeze.setItem(1, frzall);
                freeze.setItem(4, unfrzall);
                freeze.setItem(8, exit);
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(ChatColor.BLUE + "Usage: /freeze [player]");
                return true;
            }

            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage(ChatColor.BLUE + "ERROR: Player is offline!");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (!playerdata.getPlayerDataBoolean("Players." + target.getName() + ".Frozen")) {
                try {
                    playerdata.setPlayerDataValue("Players." + target.getName() + ".Frozen", true);
                    playerdata.getPlayerDataConfig().save(cc.playerdataf);
                } catch (IOException e) {
                    e.printStackTrace();
                    return true;
                }
                target.sendMessage(ChatColor.BLUE + "You have been frozen!");
                sender.sendMessage(ChatColor.BLUE + "You have frozen " + target.getDisplayName());
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void freezeMenuClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        ItemMeta clickedm = clicked.getItemMeta();
        Player player = (Player) event.getWhoClicked();

        if (clickedm.equals(exitm)) {
            player.closeInventory();
            event.setCancelled(true);
        }

        if (clickedm.equals(frzallm)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "freezeall");
            player.closeInventory();
            player.sendMessage(ChatColor.BLUE + "You have frozen the entire server!");
            event.setCancelled(true);
        }

        if (clickedm.equals(unfrzallm)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unfreezeall");
            player.closeInventory();
            player.sendMessage(ChatColor.BLUE + "You have unfrozen the entire server!");
            event.setCancelled(true);
        }

        if (clickedm.equals(frzm)) {
            Inventory freezeplayer = Bukkit.createInventory(player, 54,
                    ChatColor.translateAlternateColorCodes('&', inventories.getInventoriesString("Inventories.Freeze Players.Title")));
            player.closeInventory();
            player.openInventory(freezeplayer);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!(playerdata.getPlayerDataBoolean("Players." + p.getName() + ".Frozen"))) {
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setDisplayName(p.getName());
                    skull.setItemMeta(meta);
                    freezeplayer.addItem(skull);
                }
                event.setCancelled(true);
            }
        }

        if (clickedm.equals(unfrzm)) {
            Inventory unfreezeplayer = Bukkit.createInventory(player, 54,
                    ChatColor.translateAlternateColorCodes('&', inventories.getInventoriesString("Inventories.Unfreeze Players.Title")));
            player.closeInventory();
            player.openInventory(unfreezeplayer);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (playerdata.getPlayerDataBoolean("Players." + p.getName() + ".Frozen")) {
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setDisplayName(p.getName());
                    skull.setItemMeta(meta);
                    unfreezeplayer.addItem(skull);
                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void freezePlayerMenus(InventoryClickEvent event) {
        if (event.getClickedInventory().getName().equals(ChatColor.translateAlternateColorCodes
                ('&', inventories.getInventoriesString("Inventories.Freeze Players.Title")))) {
            ItemMeta skull = event.getCurrentItem().getItemMeta();
            Player player = Bukkit.getPlayer(skull.getDisplayName());
            String name = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "freeze " + name);
            player.closeInventory();
        }

        if (event.getClickedInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes
                ('&', inventories.getInventoriesString("Inventories.Unfreeze Players.Title")))) {
            ItemMeta skull = event.getCurrentItem().getItemMeta();
            Player player = Bukkit.getPlayer(skull.getDisplayName());
            String name = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unfreeze " + name);
            player.closeInventory();
        }
    }
}


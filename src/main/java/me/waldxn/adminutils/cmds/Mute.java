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

public class Mute implements CommandExecutor, Listener {

    private PlayerDataConfig playerdata;
    private ConfigCreator cc;
    private InventoriesConfig inventories;

    private ItemStack mute, unmute, list, exit;
    private ItemMeta mutem, unmutem, listm, exitm;

    public Mute(PlayerDataConfig pdc, ConfigCreator creator, InventoriesConfig ic) {
        this.playerdata = pdc;
        this.cc = creator;
        this.inventories = ic;
        createMenuItems();
    }

    /* Commands */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (sender.hasPermission("utils.mute")) {

            if (args.length == 0) {
                Player player = (Player) sender;
                Inventory muteinv = Bukkit.createInventory(player, 9,
                        ChatColor.translateAlternateColorCodes('&', inventories.getInventoriesString("Inventories.Mute.Title")));
                player.openInventory(muteinv);
                muteinv.setItem(0, mute);
                muteinv.setItem(4, unmute);
                muteinv.setItem(8, exit);
                return true;
            }


            if (args.length != 1) {
                sender.sendMessage(ChatColor.BLUE + "Usage: /mute [player]");
                return false;
            }

            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage(ChatColor.BLUE + "ERROR: Player is offline!");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (!playerdata.getPlayerDataBoolean("Players." + target.getName() + ".Muted")) {
                try {
                    playerdata.setPlayerDataValue("Players." + target.getName() + ".Muted", true);
                    playerdata.getPlayerDataConfig().save(cc.playerdataf);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                sender.sendMessage(ChatColor.BLUE + "You have successfully muted " + target.getName() + "!");
                target.sendMessage(ChatColor.BLUE + "You have been muted!");
                return true;
            } else {
                sender.sendMessage(ChatColor.BLUE + target.getName() + " is already muted!");
                return false;
            }
        }
        return false;
    }

     /* GUI Items */

    private void createMenuItems() {
        mute = new ItemStack(Material.WOOL, 1, (short) 14);
        unmute = new ItemStack(Material.WOOL, 1, (short) 8);
        exit = new ItemStack(Material.BED, 1);

        mutem = mute.getItemMeta();
        unmutem = unmute.getItemMeta();
        exitm = exit.getItemMeta();

        mutem.setDisplayName(ChatColor.GREEN + "Mute a player");
        unmutem.setDisplayName(ChatColor.GREEN + "Unmute a player");
        exitm.setDisplayName(ChatColor.GOLD + "Close Menu");

        mute.setItemMeta(mutem);
        unmute.setItemMeta(unmutem);
        exit.setItemMeta(exitm);
    }

    /* GUI */

    @EventHandler
    public void onMuteClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        ItemMeta clickedm = clicked.getItemMeta();
        Player player = (Player) event.getWhoClicked();

        if (clicked.getType() == Material.AIR) {
            event.setCancelled(true);
            return;
        }

        if (clicked.equals(exit)) {
            player.closeInventory();
            event.setCancelled(true);
        }

        if (clicked.equals(mute)) {
            Inventory muteplayer = Bukkit.createInventory(player, 54,
                    ChatColor.translateAlternateColorCodes('&', inventories.getInventoriesString("Inventories.Mute Players.Title")));
            player.closeInventory();
            player.openInventory(muteplayer);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!(playerdata.getPlayerDataBoolean("Players." + p.getName() + ".Muted"))) {
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setDisplayName(p.getName());
                    skull.setItemMeta(meta);
                    muteplayer.addItem(skull);
                }
                event.setCancelled(true);
            }
        }

        if (clicked.equals(unmute)) {
            Inventory unmuteplayer = Bukkit.createInventory(player, 54,
                    ChatColor.translateAlternateColorCodes('&', inventories.getInventoriesString("Inventories.Unmute Players.Title")));
            player.closeInventory();
            player.openInventory(unmuteplayer);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (playerdata.getPlayerDataBoolean("Players." + p.getName() + ".Muted")) {
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setDisplayName(p.getName());
                    skull.setItemMeta(meta);
                    unmuteplayer.addItem(skull);
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void mutedPlayerMenu(InventoryClickEvent event) {

        ItemStack clicked = event.getCurrentItem();
        ItemMeta clickedm = clicked.getItemMeta();

        if (event.getCurrentItem().getType() == Material.AIR) {
            event.setCancelled(true);
            return;
        }


        if (event.getClickedInventory().getName().equals(ChatColor.translateAlternateColorCodes
                ('&', inventories.getInventoriesString("Inventories.Mute Players.Title")))) {
            ItemMeta skull = event.getCurrentItem().getItemMeta();
            Player player = Bukkit.getPlayer(skull.getDisplayName());
            String name = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute " + name);
            player.closeInventory();
        }

        if (event.getClickedInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes
                ('&', inventories.getInventoriesString("Inventories.Unmute Players.Title")))) {
            ItemMeta skull = event.getCurrentItem().getItemMeta();
            Player player = Bukkit.getPlayer(skull.getDisplayName());
            String name = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unmute " + name);
            player.closeInventory();
        }
    }

}
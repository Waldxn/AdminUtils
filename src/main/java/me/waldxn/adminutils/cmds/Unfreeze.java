package me.waldxn.adminutils.cmds;

import me.waldxn.adminutils.utils.ConfigCreator;
import me.waldxn.adminutils.utils.PlayerDataConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Unfreeze implements CommandExecutor {

    private ConfigCreator cc;
    private PlayerDataConfig playerdata;

    public Unfreeze(PlayerDataConfig pdc, ConfigCreator creator) {
        this.cc = creator;
        this.playerdata = pdc;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (sender.hasPermission("utils.freeze")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.BLUE + "Usage: /unfreeze [player]");
                return false;
            }

            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage(ChatColor.BLUE + "ERROR: Player is offline!");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (playerdata.getPlayerDataBoolean("Players." + target.getName() + ".Frozen")) {
                try {
                    playerdata.setPlayerDataValue("Players." + target.getName() + ".Frozen", false);
                    playerdata.getPlayerDataConfig().save(cc.playerdataf);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                target.sendMessage(ChatColor.BLUE + "You have been unfrozen!");
                sender.sendMessage(ChatColor.BLUE + "You have unfrozen " + target.getDisplayName());
                return true;
            }
        }
        return false;
    }
}

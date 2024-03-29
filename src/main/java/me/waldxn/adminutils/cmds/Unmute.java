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

public class Unmute implements CommandExecutor {

    private PlayerDataConfig playerdata;
    private ConfigCreator cc;

    public Unmute(PlayerDataConfig pdc, ConfigCreator creator) {
        this.playerdata = pdc;
        this.cc = creator;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (sender.hasPermission("utils.mute")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.BLUE + "Usage: /mute [player]");
                return false;
            }

            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage(ChatColor.BLUE + "ERROR: Player is offline!");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (playerdata.getPlayerDataBoolean("Players." + target.getName() + ".Muted")) {
                try {
                    playerdata.setPlayerDataValue("Players." + target.getName() + ".Muted", false);
                    playerdata.getPlayerDataConfig().save(cc.playerdataf);
                    target.sendMessage("Worked");
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                sender.sendMessage(ChatColor.BLUE + "You have successfully unmuted " + target.getName() + "!");
                target.sendMessage(ChatColor.BLUE + "You have been unmuted!");
                return true;
            } else {
                sender.sendMessage(ChatColor.BLUE + target.getName() + " is not muted!");
                return false;
            }
        }
        return false;
    }
}
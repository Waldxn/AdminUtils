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

public class FreezeAll implements CommandExecutor {

    private ConfigCreator cc;
    private PlayerDataConfig playerdata;

    public FreezeAll(PlayerDataConfig pdc, ConfigCreator creator) {
        this.cc = creator;
        this.playerdata = pdc;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (sender.hasPermission("utils.freezeall")) {
            if (args.length != 0) {
                sender.sendMessage(ChatColor.BLUE + "Usage: /freezeall");
                return false;
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!(playerdata.getPlayerDataBoolean("Players." + p.getName() + ".Frozen")) && !(p.hasPermission("utils.freezeall.bypass"))) {
                    try {
                        playerdata.setPlayerDataValue("Players." + p.getName() + ".Frozen", true);
                        playerdata.getPlayerDataConfig().save(cc.playerdataf);
                        p.sendMessage(ChatColor.BLUE + "The entire server has been frozen!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
            sender.sendMessage(ChatColor.BLUE + "You have frozen the entire server!");
        }
        return true;
    }
}
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

public class UnfreezeAll implements CommandExecutor {

    private ConfigCreator cc;
    private PlayerDataConfig playerdata;

    public UnfreezeAll(PlayerDataConfig pdc, ConfigCreator creator) {
        this.cc = creator;
        this.playerdata = pdc;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length != 0) {
            sender.sendMessage(ChatColor.BLUE + "Usage: /unfreezeall");
            return false;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (playerdata.getPlayerDataBoolean("Players." + p.getName() + ".Frozen")) {
                try {
                    playerdata.setPlayerDataValue("Players." + p.getName() + ".Frozen", false);
                    playerdata.getPlayerDataConfig().save(cc.playerdataf);
                    p.sendMessage(ChatColor.BLUE + "The entire server has been unfrozen!");
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        sender.sendMessage(ChatColor.BLUE + "You have unfrozen the entire server!");

        return true;
    }
}

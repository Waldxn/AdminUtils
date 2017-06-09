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

public class Nickname implements CommandExecutor {

    private ConfigCreator cc;
    private PlayerDataConfig playerdata;

    public Nickname(PlayerDataConfig pdc, ConfigCreator creator) {
        this.cc = creator;
        this.playerdata = pdc;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (sender.hasPermission("utils.nickname")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args[0].equalsIgnoreCase("reset")) {
                        try {
                            playerdata.getPlayerDataConfig().set("Players." + player.getName() + ".Nickname", player.getName());
                            playerdata.getPlayerDataConfig().save(cc.playerdataf);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                        player.sendMessage(ChatColor.BLUE + "You have reset your display name!");
                        player.setDisplayName(player.getName());
                        return true;
                    } else {
                        String nicktemp = args[0];
                        String nick = nicktemp + ChatColor.RESET;
                        try {
                            playerdata.getPlayerDataConfig().set("Players." + player.getName() + ".Nickname", nick);
                            playerdata.getPlayerDataConfig().save(cc.playerdataf);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                        player.setDisplayName(ChatColor.translateAlternateColorCodes('&', playerdata.getPlayerDataString("Players." + player.getName() + ".Nickname")));
                        player.sendMessage(ChatColor.BLUE + "You have changed your display name to " +
                                ChatColor.translateAlternateColorCodes('&', playerdata.getPlayerDataString("Players." + player.getName() + ".Nickname")));
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.BLUE + "Usage: /nickname [nickname] [player]");
                }
            }
        }

        if (sender.hasPermission("utils.nickname.others")) {
            if (args.length == 2) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player target = Bukkit.getPlayer(args[1]);
                    String nicktemp = args[0];
                    String nick = nicktemp + ChatColor.RESET;
                    try {
                        playerdata.getPlayerDataConfig().set("Players." + target.getName() + ".Nickname", nick);
                        playerdata.getPlayerDataConfig().save(cc.playerdataf);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                    target.sendMessage(ChatColor.BLUE + "Your display name has been set to " +
                            ChatColor.translateAlternateColorCodes('&', playerdata.getPlayerDataString("Players." + target.getName() + ".Nickname")));
                    sender.sendMessage(ChatColor.BLUE + "You have changed " + target.getName() + "'s display name to " +
                            ChatColor.translateAlternateColorCodes('&', playerdata.getPlayerDataString("Players." + target.getName() + ".Nickname")));
                    target.setDisplayName(ChatColor.translateAlternateColorCodes('&', playerdata.getPlayerDataString("Players." + target.getName() + ".Nickname")));
                    return true;
                }
            }
        }
        return false;
    }
}

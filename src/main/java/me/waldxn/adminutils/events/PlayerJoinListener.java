package me.waldxn.adminutils.events;

import me.waldxn.adminutils.utils.ConfigCreator;
import me.waldxn.adminutils.utils.PlayerDataConfig;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private ConfigCreator cc;
    private PlayerDataConfig playerdata;

    public PlayerJoinListener(PlayerDataConfig pdc, ConfigCreator creator) {
        this.cc = creator;
        this.playerdata = pdc;
    }

    @EventHandler
    public void onNickedJoin(PlayerJoinEvent event) {
        if (!(event.getPlayer().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&',
                playerdata.getPlayerDataString("Players." + event.getPlayer().getName() + ".Nickname"))))) {
            event.getPlayer().setDisplayName(ChatColor.translateAlternateColorCodes('&',
                    playerdata.getPlayerDataString("Players." + event.getPlayer().getName() + ".Nickname")));
        }
    }
}

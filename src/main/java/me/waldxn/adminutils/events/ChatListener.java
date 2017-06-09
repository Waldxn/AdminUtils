package me.waldxn.adminutils.events;

import me.waldxn.adminutils.utils.PlayerDataConfig;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private PlayerDataConfig playerdata;

    public ChatListener(PlayerDataConfig pd) {
        this.playerdata = pd;
    }

    /* Muted Listener */
    @EventHandler
    public void onMutedChat(AsyncPlayerChatEvent event){
        if (playerdata.getPlayerDataBoolean("Players." + event.getPlayer().getName() + ".Muted")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.BLUE + "You are not permitted to speak!");
        }
    }

    /* Nickname Listener */
    @EventHandler
    public void onNicknamedChat(AsyncPlayerChatEvent event){
        if (!(event.getPlayer().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&',
                playerdata.getPlayerDataString("Players." + event.getPlayer().getName() + ".Nickname"))))) {
            event.getPlayer().setDisplayName(ChatColor.translateAlternateColorCodes('&',
                    playerdata.getPlayerDataString("Players." + event.getPlayer().getName() + ".Nickname")));
        }
    }
}

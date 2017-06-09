package me.waldxn.adminutils.events;

import me.waldxn.adminutils.AdminUtils;
import me.waldxn.adminutils.utils.ConfigCreator;
import me.waldxn.adminutils.utils.PlayerDataConfig;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerMoveListener implements Listener {

    private ConfigCreator cc;
    private PlayerDataConfig playerdata;
    private AdminUtils plugin;

    public PlayerMoveListener(PlayerDataConfig pdc, ConfigCreator creator, AdminUtils pl) {
        this.cc = creator;
        this.playerdata = pdc;
        this.plugin = pl;
    }

    @EventHandler
    public void onFreezeMove(PlayerMoveEvent event) {
        if (playerdata.getPlayerDataBoolean("Players." + event.getPlayer().getName() + ".Frozen")) {
            event.setCancelled(true);
        }
    }
}

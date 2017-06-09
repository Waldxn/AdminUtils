package me.waldxn.adminutils;

import me.waldxn.adminutils.cmds.*;
import me.waldxn.adminutils.events.ChatListener;
import me.waldxn.adminutils.events.PlayerMoveListener;
import me.waldxn.adminutils.utils.ConfigCreator;
import me.waldxn.adminutils.utils.PlayerDataConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class AdminUtils extends JavaPlugin {

    private ConfigCreator cc = new ConfigCreator(this);
    private PlayerDataConfig playerdata = new PlayerDataConfig(cc);

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        logger.info("Enabling AdminUtils");
        cc.createConfig();
        cc.createPlayerDataConfig();
        registerEvents();
        getCommand("mute").setExecutor(new Mute(playerdata, cc));
        getCommand("unmute").setExecutor(new Unmute(playerdata, cc));
        getCommand("nickname").setExecutor(new Nickname(playerdata, cc));
        getCommand("freeze").setExecutor(new Freeze(playerdata, cc));
        getCommand("unfreeze").setExecutor(new Unfreeze(playerdata, cc));
        getCommand("freezeall").setExecutor(new FreezeAll(playerdata, cc));
        getCommand("unfreezeall").setExecutor(new UnfreezeAll(playerdata, cc));
    }

    @Override
    public void onDisable() {
        Logger logger = getLogger();
        logger.info("Disabling AdminUtils");
    }

    private void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(playerdata, this);
        pm.registerEvents(new ChatListener(playerdata), this);
        pm.registerEvents(new PlayerMoveListener(playerdata, cc, this), this);
    }
}

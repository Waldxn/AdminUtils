package me.waldxn.adminutils;

import me.waldxn.adminutils.cmds.*;
import me.waldxn.adminutils.events.ChatListener;
import me.waldxn.adminutils.events.PlayerJoinListener;
import me.waldxn.adminutils.events.PlayerMoveListener;
import me.waldxn.adminutils.utils.ConfigCreator;
import me.waldxn.adminutils.utils.InventoriesConfig;
import me.waldxn.adminutils.utils.PlayerDataConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public class AdminUtils extends JavaPlugin {

    private ConfigCreator cc = new ConfigCreator(this);
    private PlayerDataConfig playerdata = new PlayerDataConfig(cc);
    private InventoriesConfig ic = new InventoriesConfig(cc);
    private Freeze freeze = new Freeze(playerdata, cc, ic);
    private Mute mute = new Mute(playerdata, cc, ic);

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        logger.info("Enabling AdminUtils");
        registerEvents();
        registerCommands();
        try {
            createConfigFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        pm.registerEvents(new PlayerJoinListener(playerdata, cc), this);
        pm.registerEvents(freeze, this);
        pm.registerEvents(mute, this);
    }

    private void registerCommands(){
        getCommand("mute").setExecutor(mute);
        getCommand("unmute").setExecutor(new Unmute(playerdata, cc));
        getCommand("nickname").setExecutor(new Nickname(playerdata, cc));
        getCommand("freeze").setExecutor(freeze);
        getCommand("unfreeze").setExecutor(new Unfreeze(playerdata, cc));
        getCommand("freezeall").setExecutor(new FreezeAll(playerdata, cc));
        getCommand("unfreezeall").setExecutor(new UnfreezeAll(playerdata, cc));
    }

    private void createConfigFiles() throws IOException {
        cc.createConfig();
        cc.createPlayerDataConfig();
        cc.createInventoriesConfig();
    }
}

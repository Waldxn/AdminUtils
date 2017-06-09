package me.waldxn.adminutils.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerDataConfig implements Listener {

    private ConfigCreator cc;

    public PlayerDataConfig(ConfigCreator creator){
        this.cc = creator;
    }

    public FileConfiguration getPlayerDataConfig(){
        return cc.playerdata;
    }

    public void setPlayerDataValue(String path, Object value) throws IOException {
        cc.playerdata.set(path, value);
        cc.playerdata.save(cc.playerdataf);
    }

    public Boolean getPlayerDataBoolean(String path){
        return cc.playerdata.getBoolean(path);
    }
    public String getPlayerDataString(String path){
        return cc.playerdata.getString(path);
    }
    public int getPlayerDataInt(String path){
        return cc.playerdata.getInt(path);
    }
    public double getPlayerDataDouble(String path){
        return cc.playerdata.getDouble(path);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        if (!(cc.playerdata.contains("Players." + event.getPlayer().getName()))){
            cc.playerdata.set("Players." + event.getPlayer().getName(), null);
            cc.playerdata.set("Players." + event.getPlayer().getName() + ".Muted", false);
            cc.playerdata.set("Players." + event.getPlayer().getName() + ".Nickname", null);
            cc.playerdata.save(cc.playerdataf);
        }
    }

}

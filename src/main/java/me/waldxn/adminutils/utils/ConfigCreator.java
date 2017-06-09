package me.waldxn.adminutils.utils;

import me.waldxn.adminutils.AdminUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigCreator {

    private AdminUtils plugin;

    public ConfigCreator(AdminUtils pl){
        this.plugin = pl;
    }

    @SuppressWarnings("WeakerAccess")
    public File configf, playerdataf;
    @SuppressWarnings("WeakerAccess")
    public FileConfiguration config, playerdata;

    public void createConfig() {

        configf = new File(plugin.getDataFolder(), "config.yml");

        if (!configf.exists()) {
            //noinspection ResultOfMethodCallIgnored
            configf.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(configf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void createPlayerDataConfig() {

        playerdataf = new File(plugin.getDataFolder(), "playerdata.yml");

        if (!playerdataf.exists()) {
            //noinspection ResultOfMethodCallIgnored
            playerdataf.getParentFile().mkdirs();
            plugin.saveResource("playerdata.yml", false);
        }

        playerdata = new YamlConfiguration();

        try {
            playerdata.load(playerdataf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

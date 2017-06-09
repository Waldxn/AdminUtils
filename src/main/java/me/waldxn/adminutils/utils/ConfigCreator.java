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
    public File configf, playerdataf, inventoriesf;
    @SuppressWarnings("WeakerAccess")
    public FileConfiguration config, playerdata, inventories;

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

    public void createInventoriesConfig() throws IOException {
        inventoriesf = new File(plugin.getDataFolder(), "inventories.yml");

        if (!inventoriesf.exists()) {
            //noinspection ResultOfMethodCallIgnored
            inventoriesf.getParentFile().mkdirs();
            plugin.saveResource("playerdata.yml", false);
        }

        inventories = new YamlConfiguration();

        try {
            inventories.load(inventoriesf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        inventories.set("Inventories.Freeze.Title", "&bFreeze Menu");
        inventories.set("Inventories.Freeze Players.Title", "&bFreeze Players Menu");
        inventories.set("Inventories.Unfreeze Players.Title", "&bUnfreeze Players Menu");
        inventories.save(inventoriesf);
    }
}

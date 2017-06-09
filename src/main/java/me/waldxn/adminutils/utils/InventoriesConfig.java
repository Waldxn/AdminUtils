package me.waldxn.adminutils.utils;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

public class InventoriesConfig {

    private ConfigCreator cc;

    public InventoriesConfig(ConfigCreator creator) {
        this.cc = creator;
    }

    public FileConfiguration getInventoriesConfig() {
        return cc.inventories;
    }

    public void setInventoriesValue(String path, Object value) throws IOException {
        cc.inventories.set(path, value);
        cc.inventories.save(cc.inventoriesf);
    }

    public Boolean getInventoriesBoolean(String path) {
        return cc.inventories.getBoolean(path);
    }

    public String getInventoriesString(String path) {
        return cc.inventories.getString(path);
    }

    public int getInventoriesInt(String path) {
        return cc.inventories.getInt(path);
    }

    public double getInventoriesDouble(String path) {
        return cc.inventories.getDouble(path);
    }

}

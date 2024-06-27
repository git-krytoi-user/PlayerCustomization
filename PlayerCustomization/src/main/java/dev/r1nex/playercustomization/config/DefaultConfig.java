package dev.r1nex.playercustomization.config;

import dev.r1nex.playercustomization.PlayerCustomization;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class DefaultConfig {
    private final FileConfiguration fc;

    public DefaultConfig(PlayerCustomization plugin) {
        if (!new File(plugin.getDataFolder() + File.separator + "config.yml").exists()) {
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
        }
        this.fc = plugin.getConfig();
    }

    public String getString(String path) {
        return fc.getString(path);
    }

    public List<String> getStringList(String path) {
        return fc.getStringList(path);
    }

    public int getInt(String path) {
        return fc.getInt(path);
    }
}

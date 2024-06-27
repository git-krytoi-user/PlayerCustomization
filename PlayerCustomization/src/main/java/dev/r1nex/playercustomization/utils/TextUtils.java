package dev.r1nex.playercustomization.utils;

import org.bukkit.ChatColor;

public class TextUtils {

    public String parseTextColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}

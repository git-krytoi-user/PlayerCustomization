package dev.r1nex.playercustomization.utils;

import dev.r1nex.playercustomization.PlayerCustomization;
import dev.r1nex.playercustomization.data.Tag;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class TagUtils {

    private final PlayerCustomization plugin;

    public TagUtils(PlayerCustomization plugin) {
        this.plugin = plugin;
    }

    public void addPlayerTag(CommandSender sender, UUID playerUuid, String displayName) {
        Tag tag = new Tag(playerUuid, displayName);
        plugin.getPlayerTags().add(tag);

        String message = plugin
                .getDefaultConfig()
                .getString("messages.add-tag")
                .replace("%tag%", displayName)
        ;
        sender.sendMessage(plugin.getTextUtils().parseTextColor(message));
        plugin.getMySQL().insertPlayerTag(playerUuid, displayName);
    }

    public void setPlayerTag(Player player, Tag tag) {
        String command = "lp user %player_name% meta setsuffix 100 %suffix%"
                .replace("%player_name%", player.getName())
                .replace("%suffix%", tag.getDisplayName());

        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
        List<String> message = plugin.getDefaultConfig().getStringList("messages.set-tag");
        message.forEach(string -> player.sendMessage(plugin.getTextUtils().parseTextColor(string)));
    }

    public List<Tag> getPlayerTags(UUID playerUuid) {
        List<Tag> tags = new ArrayList<>();
        for (Tag tag : plugin.getPlayerTags()) {
            if (tag.getUuid().toString().equals(playerUuid.toString())) {
                tags.add(tag);
            }
        }

        return tags;
    }

    public void disablePlayerTag(Player player) {
        String command = "lp user %player_name% meta removesuffix 100"
                .replace("%player_name%", player.getName());

        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
        List<String> message = plugin.getDefaultConfig().getStringList("messages.unset-tag");
        message.forEach(string -> player.sendMessage(plugin.getTextUtils().parseTextColor(string)));
    }
}

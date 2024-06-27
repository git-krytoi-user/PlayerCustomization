package dev.r1nex.playercustomization.command;

import dev.r1nex.playercustomization.PlayerCustomization;
import dev.r1nex.playercustomization.gui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TagCommand extends AbstractCommand {

    private final PlayerCustomization plugin;

    public TagCommand(PlayerCustomization plugin) {
        super(plugin, "tags");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {

        if (args.length == 0) {
            GuiManager guiManager = new GuiManager(plugin, "&6Ваши кастомные титулы.", 6);
            guiManager.showAllTags((Player) sender);
            return;
        }

        if (args[0].equals("add")) {
            if (!sender.hasPermission("player.tags.add")) {
                String message = plugin.getDefaultConfig().getString("messages.have-permission");
                sender.sendMessage(plugin.getTextUtils().parseTextColor(message));
                return;
            }

            if (args.length < 3) {
                sender.sendMessage(plugin.getTextUtils().parseTextColor("&6> &e/tags add [игрок] [название]"));
                return;
            }

            String playerName = args[1];
            String tagName = args[2];

            if (tagName.length() < plugin.getDefaultConfig().getInt("settings.min-tag-characters")) {
                String message = plugin.getDefaultConfig().getString("messages.min-tag-characters");
                sender.sendMessage(plugin.getTextUtils().parseTextColor(message));
                return;
            }

            if (tagName.length() > plugin.getDefaultConfig().getInt("settings.max-tag-characters")) {
                String message = plugin.getDefaultConfig().getString("messages.max-tag-characters");
                sender.sendMessage(plugin.getTextUtils().parseTextColor(message));
                return;
            }

            Player playerTarget = Bukkit.getPlayerExact(playerName);
            if (playerTarget == null) {
                sender.sendMessage(plugin.getTextUtils().parseTextColor(
                        "&cПРЕДУПРЕЖДЕНИЕ: &fЭтого игрока никогда не было на сервере.")
                );
                return;
            }

            if (!playerTarget.isOnline()) {
                sender.sendMessage(plugin.getTextUtils().parseTextColor(
                        "&cПРЕДУПРЕЖДЕНИЕ: &fЭтот игрок не в сети.")
                );
                return;
            }

            plugin.getTagUtils().addPlayerTag(sender, playerTarget.getUniqueId(), tagName);
        }
    }

    @Override
    public List<String> completer(CommandSender sender, Command command, String[] args) {
        return null;
    }
}

package dev.r1nex.playercustomization.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dev.r1nex.playercustomization.PlayerCustomization;
import dev.r1nex.playercustomization.data.Tag;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    private final PlayerCustomization plugin;

    private final String title;

    private final int rows;

    public GuiManager(PlayerCustomization plugin, String title, int rows) {
        this.plugin = plugin;
        this.title = title;
        this.rows = rows;
    }

    public void showAllTags(Player player) {
        if (plugin.getTagUtils().getPlayerTags(player.getUniqueId()).isEmpty()) {
            String message = plugin.getDefaultConfig().getString("messages.no-tags");
            player.sendMessage(plugin.getTextUtils().parseTextColor(message));
            return;
        }

        ChestGui chestGui = new ChestGui(rows, plugin.getTextUtils().parseTextColor(title));
        chestGui.setOnGlobalClick(event -> event.setCancelled(true));
        PaginatedPane pane = new PaginatedPane(1, 1, 7, 4);
        List<GuiItem> items = new ArrayList<>();

        List<Tag> tags = plugin.getTagUtils().getPlayerTags(player.getUniqueId());
        for (Tag tag : tags) {
            List<String> loreItem = new ArrayList<>();
            loreItem.add(" ");
            loreItem.add(plugin.getTextUtils().parseTextColor("&f| &eЭто Ваш титул."));
            loreItem.add(plugin.getTextUtils().parseTextColor("&f| &eЧтобы его установить, нажмите на него."));
            loreItem.add(" ");

            ItemStack itemStack = new ItemStack(Material.NAME_TAG);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(Component.text(plugin.getTextUtils().parseTextColor(tag.getDisplayName())));
            itemMeta.lore(plugin.getItemUtils().getLore(loreItem));

            itemStack.setItemMeta(itemMeta);
            GuiItem guiItem = new GuiItem(itemStack, event -> {
                plugin.getTagUtils().setPlayerTag(player, tag);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1);
            });
            items.add(guiItem);
        }
        pane.populateWithGuiItems(items);

        ItemStack nextPage = plugin.getItemUtils().createItem(
                "&aСлед. страница", Material.ARROW, 1
        );
        ItemStack previousPage = plugin.getItemUtils().createItem(
                "&4Пред. страница", Material.RED_WOOL, 1
        );
        ItemStack resetTag = plugin.getItemUtils().createItem(
                "&6> &fУбрать титул", Material.TOTEM_OF_UNDYING, 1
        );
        StaticPane navigation = new StaticPane(0, 1, 9, 5);
        navigation.addItem(new GuiItem(nextPage, event -> {
            if (pane.getPage() < pane.getPages() - 1) {
                pane.setPage(pane.getPage() + 1);

                chestGui.update();
            }
        }), 6, 4);
        navigation.addItem(new GuiItem(previousPage, event -> {
            if (pane.getPage() > 0) {
                pane.setPage(pane.getPage() - 1);

                chestGui.update();
            }
        }), 2, 4);
        navigation.addItem(new GuiItem(resetTag, event -> {
            plugin.getTagUtils().disablePlayerTag(player);
            player.closeInventory();
        }), 4, 4);

        chestGui.addPane(navigation);
        chestGui.addPane(pane);
        chestGui.show(player);
    }
}

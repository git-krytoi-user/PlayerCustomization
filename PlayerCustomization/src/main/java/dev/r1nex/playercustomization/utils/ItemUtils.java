package dev.r1nex.playercustomization.utils;

import dev.r1nex.playercustomization.PlayerCustomization;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    private final PlayerCustomization plugin;

    public ItemUtils(PlayerCustomization plugin) {
        this.plugin = plugin;
    }

    public List<Component> getLore(List<String> lore) {
        List<Component> components = new ArrayList<>(lore.size());
        lore.forEach(string -> components.add(Component.text(string)));
        return components;
    }

    public ItemStack createItem(String displayName, Material material, int amount) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(plugin.getTextUtils().parseTextColor(displayName)));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}

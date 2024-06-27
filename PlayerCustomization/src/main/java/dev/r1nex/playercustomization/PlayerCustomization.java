package dev.r1nex.playercustomization;

import dev.r1nex.playercustomization.command.TagCommand;
import dev.r1nex.playercustomization.config.DefaultConfig;
import dev.r1nex.playercustomization.data.Tag;
import dev.r1nex.playercustomization.database.MySQL;
import dev.r1nex.playercustomization.utils.ItemUtils;
import dev.r1nex.playercustomization.utils.TagUtils;
import dev.r1nex.playercustomization.utils.TextUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class PlayerCustomization extends JavaPlugin {

    private TagUtils tagUtils;
    private TextUtils textUtils;
    private ItemUtils itemUtils;
    private DefaultConfig defaultConfig;
    private MySQL mySQL;
    private List<Tag> playerTags;

    @Override
    public void onEnable() {
        tagUtils = new TagUtils(this);
        textUtils = new TextUtils();
        itemUtils = new ItemUtils(this);
        defaultConfig = new DefaultConfig(this);
        new TagCommand(this);

        mySQL = new MySQL(
                "77.222.37.34",
                3306, "s1_anarchy",
                "u1_gRBBDnBuqh", "8al=WU4K5+AEeXDf=.KYfb=2"
        );
        playerTags = mySQL.getAllTags();
    }

    public List<Tag> getPlayerTags() {
        return playerTags;
    }

    public TagUtils getTagUtils() {
        return tagUtils;
    }

    public TextUtils getTextUtils() {
        return textUtils;
    }

    public ItemUtils getItemUtils() {
        return itemUtils;
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}

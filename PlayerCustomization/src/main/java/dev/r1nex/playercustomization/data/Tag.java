package dev.r1nex.playercustomization.data;

import java.util.UUID;

public class Tag {
    private final UUID uuid;
    private final String displayName;

    public Tag(UUID uuid, String displayName) {
        this.uuid = uuid;
        this.displayName = displayName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }
}

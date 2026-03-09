package com.Betulis.Game2D.game.ui.data;

import com.badlogic.gdx.graphics.Texture;

public class SpellDefinition {
    private final String id;
    private final String displayName;
    private final String configPath;
    private final Texture icon;

    public SpellDefinition(String id, String displayName, String configPath, Texture icon) {
        this.id = id;
        this.displayName = displayName;
        this.configPath = configPath;
        this.icon = icon;
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public String getConfigPath() { return configPath; }
    public Texture getIcon() { return icon; }
}

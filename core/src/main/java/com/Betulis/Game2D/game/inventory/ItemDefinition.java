package com.Betulis.Game2D.game.inventory;

import com.badlogic.gdx.graphics.Texture;

public class ItemDefinition {

    public enum ItemType { WEAPON, ARMOR, ACCESSORY, CONSUMABLE }

    private final String name;
    private final Texture icon;
    private final ItemType itemType;
    private final ItemConfig config;

    public ItemDefinition(String name, Texture icon, ItemType itemType, ItemConfig config) {
        this.name = name;
        this.icon = icon;
        this.itemType = itemType;
        this.config = config;
    }

    public String getName()       { return name; }
    public Texture getIcon()      { return icon; }
    public ItemType getItemType() { return itemType; }
    public ItemConfig getConfig() { return config; }
}

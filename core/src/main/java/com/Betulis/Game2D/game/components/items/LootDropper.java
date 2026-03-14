package com.Betulis.Game2D.game.components.items;

import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.inventory.ItemConfig;
import com.Betulis.Game2D.game.inventory.ItemDefinition;
import com.Betulis.Game2D.game.prefabs.items.WorldItemPrefab;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;

public class LootDropper extends Component {

    private final String texturePath;
    private final String configPath;
    private final float dropChance;
    private ItemDefinition itemDef;

    public LootDropper(String texturePath, String configPath, float dropChance) {
        this.texturePath = texturePath;
        this.configPath  = configPath;
        this.dropChance  = dropChance;
    }

    @Override
    public void start() {
        Texture tex = getScene().getGame().getAssets().getTexture(texturePath);
        ItemConfig cfg = new Json().fromJson(ItemConfig.class, Gdx.files.internal(configPath));
        String name = (cfg != null && cfg.name != null) ? cfg.name : "Unknown Item";
        itemDef = new ItemDefinition(name, tex, itemTypeFromSlot(cfg != null ? cfg.slot : null), cfg);
    }

    private static ItemDefinition.ItemType itemTypeFromSlot(String slot) {
        if (slot == null) return ItemDefinition.ItemType.ACCESSORY;
        switch (slot.toUpperCase()) {
            case "WEAPON":  return ItemDefinition.ItemType.WEAPON;
            case "HEAD": case "SHOULDER": case "BACK": case "CHEST":
            case "WRIST": case "HANDS": case "WAIST": case "LEGS": case "FEET":
                return ItemDefinition.ItemType.ARMOR;
            default:        return ItemDefinition.ItemType.ACCESSORY;
        }
    }

    @Override
    public void onDestroy() {
        if (itemDef == null) return;
        if (Math.random() < dropChance) {
            float ox = (float) (Math.random() * 40 - 20);
            float oy = (float) (Math.random() * 40 - 20);
            float x = transform.getWorldX() + ox;
            float y = transform.getWorldY() + oy;
            getScene().addObject(WorldItemPrefab.create(x, y, itemDef));
        }
    }
}

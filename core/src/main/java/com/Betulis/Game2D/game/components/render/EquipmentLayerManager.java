package com.Betulis.Game2D.game.components.render;

import com.Betulis.Game2D.engine.animation.CharacterState;
import com.Betulis.Game2D.engine.animation.DirectionalAnimSet;
import com.Betulis.Game2D.engine.render.LayeredSpriteRenderer;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.inventory.ItemDefinition;
import com.Betulis.Game2D.game.ui.panels.CharacterPanel;
import com.Betulis.Game2D.game.ui.widgets.EquipmentSlot;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages visual equipment layers on the player's LayeredSpriteRenderer.
 * Layer 0 = body (always present). Equipment layers start at index 1.
 * Call init(CharacterPanel) after the UI is ready.
 */
public class EquipmentLayerManager extends Component {

    private static final String[] VISUAL_SLOTS = {"head", "chest", "legs"};

    private LayeredSpriteRenderer renderer;
    private Assets assets;

    // slotKey → layer index in the renderer
    private final Map<String, Integer> slotLayerIndex = new HashMap<>();
    private int nextLayerIndex = 1;

    @Override
    public void start() {
        renderer = getGameObject().getComponent(LayeredSpriteRenderer.class);
        assets   = getScene().getGame().getAssets();
    }

    public void init(CharacterPanel panel) {
        if (panel == null) return;
        for (EquipmentSlot slot : panel.getEquipSlots()) {
            final String key = slot.getSlotKey();
            for (String vs : VISUAL_SLOTS) {
                if (key.equals(vs)) {
                    slot.setEquipListener(new EquipmentSlot.EquipListener() {
                        @Override public void onEquip(ItemDefinition item) { onItemEquipped(key, item); }
                        @Override public void onUnequip()                  { onItemUnequipped(key); }
                    });
                    break;
                }
            }
        }
    }

    private void onItemEquipped(String slotKey, ItemDefinition item) {
        if (renderer == null || item == null) return;
        if (item.getConfig() == null || item.getConfig().spriteLayer == null) return;

        String atlasPath = "equipment/" + item.getConfig().spriteLayer + ".atlas";
        TextureAtlas atlas = assets.getAtlas(atlasPath);
        if (atlas == null) return; // atlas not loaded yet — skip silently

        Map<CharacterState, DirectionalAnimSet> layer = new HashMap<>();
        layer.put(CharacterState.IDLE, new DirectionalAnimSet(atlas, "idle", 0.3f));
        layer.put(CharacterState.WALK, new DirectionalAnimSet(atlas, "walk", 0.2f));
        renderer.addLayer(layer);
        slotLayerIndex.put(slotKey, nextLayerIndex++);
    }

    private void onItemUnequipped(String slotKey) {
        if (renderer == null) return;
        Integer idx = slotLayerIndex.remove(slotKey);
        if (idx == null) return;
        renderer.removeLayersFromIndex(idx);
        nextLayerIndex = idx;
        // Shift down any layers that were above the removed one
        for (Map.Entry<String, Integer> e : slotLayerIndex.entrySet()) {
            if (e.getValue() > idx) e.setValue(e.getValue() - 1);
        }
    }
}

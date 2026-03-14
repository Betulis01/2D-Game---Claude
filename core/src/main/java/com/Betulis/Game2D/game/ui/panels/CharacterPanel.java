package com.Betulis.Game2D.game.ui.panels;

import com.Betulis.Game2D.game.ui.core.UIPanel;
import com.Betulis.Game2D.game.ui.widgets.SpellSlot;
import com.badlogic.gdx.graphics.Texture;

import java.util.Map;

public class CharacterPanel extends UIPanel {
    private static final float SLOT_SIZE = 40f;
    private static final float SLOT_GAP = 4f;

    public CharacterPanel(float screenW, float screenH, Map<String, Texture> equipTextures, Texture slotFallback) {
        super("Character", 20f, screenH / 2f - 250f, 200f, 480f);

        loadPanelBg("ui/panels/character_panel.png");

        float leftX = x + 8f;
        float rightX = x + w - SLOT_SIZE - 8f;
        float topY = y + h - 60f;
        float step = SLOT_SIZE + SLOT_GAP;

        // Left column: Head, Shoulder, Back, Chest, Wrist
        addEquipSlot("equip_head",     leftX, topY - 0 * step, equipTextures, slotFallback);
        addEquipSlot("equip_shoulder", leftX, topY - 1 * step, equipTextures, slotFallback);
        addEquipSlot("equip_back",     leftX, topY - 2 * step, equipTextures, slotFallback);
        addEquipSlot("equip_chest",    leftX, topY - 3 * step, equipTextures, slotFallback);
        addEquipSlot("equip_wrist",    leftX, topY - 4 * step, equipTextures, slotFallback);

        // Right column: Hands, Waist, Legs, Feet, Relic
        addEquipSlot("equip_hands",  rightX, topY - 0 * step, equipTextures, slotFallback);
        addEquipSlot("equip_waist",  rightX, topY - 1 * step, equipTextures, slotFallback);
        addEquipSlot("equip_legs",   rightX, topY - 2 * step, equipTextures, slotFallback);
        addEquipSlot("equip_feet",   rightX, topY - 3 * step, equipTextures, slotFallback);
        addEquipSlot("equip_relic",  rightX, topY - 4 * step, equipTextures, slotFallback);

        // Bottom row: Ring1, Ring2, Trinket1, Trinket2
        float bottomY = y + 55f;
        float centerStart = x + (w - 2 * (SLOT_SIZE + SLOT_GAP)) / 2f;
        addEquipSlot("equip_ring",    centerStart,                   bottomY, equipTextures, slotFallback);
        addEquipSlot("equip_ring",    centerStart + SLOT_SIZE + SLOT_GAP, bottomY, equipTextures, slotFallback);
        addEquipSlot("equip_trinket", centerStart,                   bottomY - step, equipTextures, slotFallback);
        addEquipSlot("equip_trinket", centerStart + SLOT_SIZE + SLOT_GAP, bottomY - step, equipTextures, slotFallback);

        // Weapon row
        float weaponY = y + 10f;
        addEquipSlot("equip_weapon", leftX,   weaponY, equipTextures, slotFallback);
        addEquipSlot("equip_weapon", rightX,  weaponY, equipTextures, slotFallback);
    }

    private void addEquipSlot(String key, float slotX, float slotY, Map<String, Texture> textures, Texture fallback) {
        Texture tex = textures.getOrDefault(key, fallback);
        add(new SpellSlot(slotX, slotY, SLOT_SIZE, false, tex));
    }
}

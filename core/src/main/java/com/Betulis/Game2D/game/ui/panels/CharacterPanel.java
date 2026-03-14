package com.Betulis.Game2D.game.ui.panels;

import com.Betulis.Game2D.game.ui.PlayerStats;
import com.Betulis.Game2D.game.ui.core.UIPanel;
import com.Betulis.Game2D.game.ui.widgets.EquipmentSlot;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CharacterPanel extends UIPanel {
    private static final float SLOT_SIZE = 40f;
    private static final float SLOT_GAP  = 4f;

    private final List<EquipmentSlot> equipSlots = new ArrayList<>();
    private PlayerStats playerStats;

    public CharacterPanel(float screenW, float screenH, Map<String, Texture> equipTextures, Texture slotFallback) {
        super("Character", 20f, screenH / 2f - 250f, 200f, 480f);

        loadPanelBg("assets/ui/panels/character_panel.png");

        float leftX  = x + 8f;
        float rightX = x + w - SLOT_SIZE - 8f;
        float topY   = y + h - 60f;
        float step   = SLOT_SIZE + SLOT_GAP;

        // Left column: Head, Shoulder, Back, Chest, Wrist
        addEquipSlot("head",     "HEAD",     "head_equip_icon",     leftX, topY - 0 * step, equipTextures, slotFallback);
        addEquipSlot("shoulder", "SHOULDER", "shoulder_equip_icon", leftX, topY - 1 * step, equipTextures, slotFallback);
        addEquipSlot("back",     "BACK",     "back_equip_icon",     leftX, topY - 2 * step, equipTextures, slotFallback);
        addEquipSlot("chest",    "CHEST",    "chest_equip_icon",    leftX, topY - 3 * step, equipTextures, slotFallback);
        addEquipSlot("wrist",    "WRIST",    "wrist_equip_icon",    leftX, topY - 4 * step, equipTextures, slotFallback);

        // Right column: Hands, Waist, Legs, Feet, Relic
        addEquipSlot("hands",  "HANDS",  "hands_equip_icon",  rightX, topY - 0 * step, equipTextures, slotFallback);
        addEquipSlot("waist",  "WAIST",  "waist_equip_icon",  rightX, topY - 1 * step, equipTextures, slotFallback);
        addEquipSlot("legs",   "LEGS",   "legs_equip_icon",   rightX, topY - 2 * step, equipTextures, slotFallback);
        addEquipSlot("feet",   "FEET",   "feet_equip_icon",   rightX, topY - 3 * step, equipTextures, slotFallback);
        addEquipSlot("relic",  "RELIC",  "relic_equip_icon",  rightX, topY - 4 * step, equipTextures, slotFallback);

        // Bottom row: Ring1, Ring2, Trinket1, Trinket2
        float bottomY     = y + 55f;
        float centerStart = x + (w - 2 * (SLOT_SIZE + SLOT_GAP)) / 2f;
        addEquipSlot("ring1",    "RING",    "ring_1_equip_icon",  centerStart,                        bottomY,        equipTextures, slotFallback);
        addEquipSlot("ring2",    "RING",    "ring_2_equip_icon",  centerStart + SLOT_SIZE + SLOT_GAP, bottomY,        equipTextures, slotFallback);
        addEquipSlot("trinket1", "TRINKET", "trinket_equip_icon", centerStart,                        bottomY - step, equipTextures, slotFallback);
        addEquipSlot("trinket2", "TRINKET", "trinket_equip_icon", centerStart + SLOT_SIZE + SLOT_GAP, bottomY - step, equipTextures, slotFallback);

        // Weapon row
        float weaponY = y + 10f;
        addEquipSlot("weapon1", "WEAPON", "weapon_equip_icon", leftX,  weaponY, equipTextures, slotFallback);
        addEquipSlot("weapon2", "WEAPON", "weapon_equip_icon", rightX, weaponY, equipTextures, slotFallback);
    }

    private void addEquipSlot(String slotKey, String slotType, String iconKey, float slotX, float slotY,
                               Map<String, Texture> textures, Texture fallback) {
        Texture tex = textures.getOrDefault(iconKey, fallback);
        EquipmentSlot slot = new EquipmentSlot(slotX, slotY, SLOT_SIZE, slotKey, slotType, tex);
        equipSlots.add(slot);
        add(slot);
    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        super.render(batch, font);
        if (playerStats != null && visible) {
            float cx    = x + w / 2f - 28f;
            float cy    = y + h - 80f;
            float lineH = 18f;
            font.setColor(Color.WHITE);
            font.draw(batch, "STR: " + playerStats.getStrength(),     cx, cy);
            font.draw(batch, "AGI: " + playerStats.getAgility(),      cx, cy - lineH);
            font.draw(batch, "STA: " + playerStats.getStamina(),      cx, cy - lineH * 2);
            font.draw(batch, "SPD: " + (int) playerStats.getSpeed(),  cx, cy - lineH * 3);
            font.draw(batch, "DMG: " + (int) playerStats.getDamage(), cx, cy - lineH * 4);
        }
    }

    public void setPlayerStats(PlayerStats stats) { this.playerStats = stats; }
    public List<EquipmentSlot> getEquipSlots()    { return equipSlots; }
}

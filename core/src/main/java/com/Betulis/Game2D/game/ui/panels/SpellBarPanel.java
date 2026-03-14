package com.Betulis.Game2D.game.ui.panels;

import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.ui.core.UIWidget;
import com.Betulis.Game2D.game.ui.data.SpellBar;
import com.Betulis.Game2D.game.ui.data.SpellDefinition;
import com.Betulis.Game2D.game.ui.widgets.SpellSlot;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpellBarPanel extends UIWidget {
    private static final int SLOT_COUNT = 3;
    public static final float SLOT_SIZE = 32f;
    public static final float SLOT_GAP = 2f;

    private final SpellSlot[] slots = new SpellSlot[SLOT_COUNT];
    private final SpellBar spellBarData;

    private static final InputBindings.Action[] SLOT_ACTIONS = {
        InputBindings.Action.SPELL_1,
        InputBindings.Action.SPELL_2,
        InputBindings.Action.SPELL_3
    };

    public SpellBarPanel(float screenW, SpellBar spellBarData, Texture slotBg, InputBindings input) {
        this.spellBarData = spellBarData;
        this.w = SLOT_COUNT * SLOT_SIZE + (SLOT_COUNT - 1) * SLOT_GAP;
        this.h = SLOT_SIZE;
        this.x = screenW / 2f - w / 2f;
        this.y = 20f;
        this.visible = true;

        for (int i = 0; i < SLOT_COUNT; i++) {
            float slotX = x + i * (SLOT_SIZE + SLOT_GAP);
            SpellSlot slot = new SpellSlot(slotX, y, SLOT_SIZE, false, slotBg);
            slot.setKeybind(input, SLOT_ACTIONS[i]);
            slots[i] = slot;
        }
    }

    public SpellSlot getSlot(int index) {
        if (index < 0 || index >= SLOT_COUNT) return null;
        return slots[index];
    }

    /** Left edge X — used by MouseSpellBarPanel to position itself. */
    public float getLeftEdgeX() { return x; }

    @Override
    public void update(float dt) {
        for (int i = 0; i < SLOT_COUNT; i++) {
            slots[i].setSpell(spellBarData.getSlot(i));
        }
    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        if (!visible) return;
        for (SpellSlot slot : slots) slot.render(batch, font);
    }

    @Override
    public boolean onMouseDown(float mx, float my) {
        if (!visible) return false;
        for (SpellSlot slot : slots) {
            if (slot.onMouseDown(mx, my)) return true;
        }
        return false;
    }

    public boolean tryDrop(float mx, float my, SpellDefinition dragging) {
        for (int i = 0; i < SLOT_COUNT; i++) {
            if (slots[i].contains(mx, my)) {
                spellBarData.setSlot(i, dragging);
                return true;
            }
        }
        return false;
    }
}

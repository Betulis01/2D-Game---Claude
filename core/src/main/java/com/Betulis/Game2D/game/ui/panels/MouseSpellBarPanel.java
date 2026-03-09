package com.Betulis.Game2D.game.ui.panels;

import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.ui.core.UIWidget;
import com.Betulis.Game2D.game.ui.data.SpellBar;
import com.Betulis.Game2D.game.ui.data.SpellDefinition;
import com.Betulis.Game2D.game.ui.widgets.SpellSlot;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Two-slot bar for mouse button spells (LMB = slot 0, RMB = slot 1).
 * Positioned to the left of the keyboard spell bar.
 */
public class MouseSpellBarPanel extends UIWidget {
    private static final int SLOT_COUNT = 2;
    private static final float SLOT_SIZE = SpellBarPanel.SLOT_SIZE;
    private static final float SLOT_GAP = SpellBarPanel.SLOT_GAP;
    private static final float BAR_GAP = 10f; // gap between this bar and the keyboard bar

    private final SpellSlot[] slots = new SpellSlot[SLOT_COUNT];
    private final SpellBar spellBarData;

    private static final InputBindings.Action[] SLOT_ACTIONS = {
        InputBindings.Action.MOUSE_SPELL_1,
        InputBindings.Action.MOUSE_SPELL_2
    };

    public MouseSpellBarPanel(SpellBarPanel keyBar, SpellBar spellBarData, Texture slotBg, InputBindings input) {
        this.spellBarData = spellBarData;
        this.w = SLOT_COUNT * SLOT_SIZE + (SLOT_COUNT - 1) * SLOT_GAP;
        this.h = SLOT_SIZE;
        this.y = keyBar.getY();
        this.x = keyBar.getRightEdgeX() + BAR_GAP;
        this.visible = true;

        for (int i = 0; i < SLOT_COUNT; i++) {
            float slotX = x + i * (SLOT_SIZE + SLOT_GAP);
            SpellSlot slot = new SpellSlot(slotX, y, SLOT_SIZE, false, slotBg);
            slot.setKeybind(input, SLOT_ACTIONS[i]);
            slots[i] = slot;
        }
    }

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

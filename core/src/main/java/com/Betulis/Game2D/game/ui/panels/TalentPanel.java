package com.Betulis.Game2D.game.ui.panels;

import com.Betulis.Game2D.game.ui.core.UIPanel;
import com.Betulis.Game2D.game.ui.widgets.SpellSlot;
import com.badlogic.gdx.graphics.Texture;

public class TalentPanel extends UIPanel {
    private static final int COLS = 3;
    private static final int ROWS = 3;
    private static final float SLOT_SIZE = 40f;
    private static final float SLOT_GAP = 8f;

    public TalentPanel(float screenW, float screenH, Texture slotBg) {
        super("Talents",
            screenW / 2f - 300f,
            screenH / 2f,
            COLS * (SLOT_SIZE + SLOT_GAP) + 20f,
            ROWS * (SLOT_SIZE + SLOT_GAP) + 40f);

        loadPanelBg("assets/ui/panels/inventory_panel.png");

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                float slotX = x + 10f + col * (SLOT_SIZE + SLOT_GAP);
                float slotY = y + 10f + (ROWS - 1 - row) * (SLOT_SIZE + SLOT_GAP);
                add(new SpellSlot(slotX, slotY, SLOT_SIZE, false, slotBg));
            }
        }
    }
}

package com.Betulis.Game2D.game.ui.panels;

import com.Betulis.Game2D.game.ui.core.UIPanel;
import com.Betulis.Game2D.game.ui.widgets.SpellSlot;
import com.badlogic.gdx.graphics.Texture;

public class BagPanel extends UIPanel {
    private static final int COLS = 4;
    private static final int ROWS = 4;
    private static final float SLOT_SIZE = 40f;
    private static final float SLOT_GAP = 4f;

    public BagPanel(float screenW, float screenH, Texture slotBg) {
        super("Bag",
            screenW - 210f,
            screenH - 220f,
            COLS * (SLOT_SIZE + SLOT_GAP) + 10f,
            ROWS * (SLOT_SIZE + SLOT_GAP) + 30f);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                float slotX = x + 5f + col * (SLOT_SIZE + SLOT_GAP);
                float slotY = y + 5f + (ROWS - 1 - row) * (SLOT_SIZE + SLOT_GAP);
                add(new SpellSlot(slotX, slotY, SLOT_SIZE, false, slotBg));
            }
        }
    }
}

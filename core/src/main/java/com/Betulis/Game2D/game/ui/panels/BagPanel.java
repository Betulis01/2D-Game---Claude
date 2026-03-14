package com.Betulis.Game2D.game.ui.panels;

import com.Betulis.Game2D.game.inventory.Inventory;
import com.Betulis.Game2D.game.ui.core.UIPanel;
import com.Betulis.Game2D.game.ui.widgets.ItemSlot;
import com.badlogic.gdx.graphics.Texture;

public class BagPanel extends UIPanel {
    private static final int COLS = 4;
    private static final int ROWS = 4;
    private static final float SLOT_SIZE = 40f;
    private static final float SLOT_GAP = 6f;

    private final ItemSlot[] itemSlots = new ItemSlot[ROWS * COLS];

    public BagPanel(float screenW, float screenH, Texture slotBg, Inventory inventory) {
        super("Bag",
            screenW - 210f,
            screenH - 220f,
            COLS * (SLOT_SIZE + SLOT_GAP) + 10f,
            ROWS * (SLOT_SIZE + SLOT_GAP) + 30f);

        loadPanelBg("assets/ui/panels/inventory_panel.png");

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int idx = row * COLS + col;
                float slotX = x + 10f + col * (SLOT_SIZE + SLOT_GAP);
                float slotY = y + 10f + (ROWS - 1 - row) * (SLOT_SIZE + SLOT_GAP);
                ItemSlot slot = new ItemSlot(slotX, slotY, SLOT_SIZE, idx, slotBg);
                if (inventory != null) slot.setItem(inventory.getSlot(idx));
                itemSlots[idx] = slot;
                add(slot);
            }
        }
    }

    public void refresh(Inventory inventory) {
        for (int i = 0; i < itemSlots.length; i++) {
            itemSlots[i].setItem(inventory.getSlot(i));
        }
    }

    public ItemSlot[] getItemSlots() { return itemSlots; }
}

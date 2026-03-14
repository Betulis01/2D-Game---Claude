package com.Betulis.Game2D.game.ui.widgets;

import com.Betulis.Game2D.game.inventory.ItemDefinition;
import com.Betulis.Game2D.game.ui.core.UIWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemSlot extends UIWidget {

    private ItemDefinition item;
    private final int slotIndex;
    private final Texture slotBg;
    private DragListener dragListener;

    public interface DragListener {
        void onDragStart(ItemDefinition item, int slotIndex);
    }

    public ItemSlot(float x, float y, float size, int slotIndex, Texture slotBg) {
        this.x = x;
        this.y = y;
        this.w = size;
        this.h = size;
        this.slotIndex = slotIndex;
        this.slotBg = slotBg;
        this.visible = true;
    }

    public void setItem(ItemDefinition item)         { this.item = item; }
    public ItemDefinition getItem()                  { return item; }
    public int getSlotIndex()                        { return slotIndex; }
    public void setDragListener(DragListener l)      { this.dragListener = l; }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        if (!visible) return;
        if (slotBg != null) {
            batch.setColor(Color.WHITE);
            batch.draw(slotBg, x, y, w, h);
        }
        if (item != null && item.getIcon() != null) {
            batch.setColor(Color.WHITE);
            batch.draw(item.getIcon(), x + 2, y + 2, w - 4, h - 4);
        }
    }

    @Override
    public boolean onMouseDown(float mx, float my) {
        if (!visible || !contains(mx, my)) return false;
        if (item != null && dragListener != null) {
            dragListener.onDragStart(item, slotIndex);
            return true;
        }
        return contains(mx, my);
    }

    @Override
    public boolean onMouseUp(float mx, float my) {
        return false;
    }
}

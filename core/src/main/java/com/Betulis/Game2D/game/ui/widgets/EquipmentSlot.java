package com.Betulis.Game2D.game.ui.widgets;

import com.Betulis.Game2D.game.inventory.ItemDefinition;
import com.Betulis.Game2D.game.ui.core.UIWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EquipmentSlot extends UIWidget {

    private final String slotKey;
    private final String slotType;
    private ItemDefinition item;
    private final Texture slotBg;
    private DragListener  dragListener;
    private EquipListener equipListener;

    public interface DragListener {
        void onDragStart(ItemDefinition item, EquipmentSlot source);
    }

    public interface EquipListener {
        void onEquip(ItemDefinition item);
        void onUnequip();
    }

    public EquipmentSlot(float x, float y, float size, String slotKey, String slotType, Texture slotBg) {
        this.x        = x;
        this.y        = y;
        this.w        = size;
        this.h        = size;
        this.slotKey  = slotKey;
        this.slotType = slotType;
        this.slotBg   = slotBg;
        this.visible  = true;
    }

    public boolean accepts(ItemDefinition candidate) {
        if (candidate == null) return false;
        String cfgSlot = candidate.getConfig() != null ? candidate.getConfig().slot : null;
        return cfgSlot != null && cfgSlot.equalsIgnoreCase(slotType);
    }

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
            dragListener.onDragStart(item, this);
            setItem(null); // fires onUnequip via equipListener
            return true;
        }
        return true;
    }

    public void setItem(ItemDefinition newItem) {
        ItemDefinition old = this.item;
        this.item = newItem;
        if (equipListener != null) {
            if (newItem != null)     equipListener.onEquip(newItem);
            else if (old != null)    equipListener.onUnequip();
        }
    }

    public String        getSlotKey()                    { return slotKey; }
    public String        getSlotType()                   { return slotType; }
    public ItemDefinition getItem()                      { return item; }
    public void          setDragListener(DragListener l) { this.dragListener = l; }
    public void          setEquipListener(EquipListener l){ this.equipListener = l; }
}

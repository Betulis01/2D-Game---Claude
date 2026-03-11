package com.Betulis.Game2D.game.ui.widgets;

import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.ui.core.UIWidget;
import com.Betulis.Game2D.game.ui.data.SpellDefinition;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpellSlot extends UIWidget {
    private SpellDefinition spell;
    private boolean isDragSource;
    private Texture slotBg;

    // Optional: when set, label is pulled live from the binding each frame
    private InputBindings inputBindings;
    private InputBindings.Action boundAction;

    private DragListener dragListener;

    public interface DragListener {
        void onDragStart(SpellDefinition spell, SpellSlot source);
    }

    public SpellSlot(float x, float y, float size, boolean isDragSource, Texture slotBg) {
        this.x = x;
        this.y = y;
        this.w = size;
        this.h = size;
        this.isDragSource = isDragSource;
        this.slotBg = slotBg;
        this.visible = true;
    }

    /** Wire up a live keybind label. The label shown in the slot corner reflects the actual binding. */
    public void setKeybind(InputBindings input, InputBindings.Action action) {
        this.inputBindings = input;
        this.boundAction = action;
    }

    public void setDragListener(DragListener listener) { this.dragListener = listener; }

    public SpellDefinition getSpell() { return spell; }
    public void setSpell(SpellDefinition spell) { this.spell = spell; }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        if (!visible) return;

        // Slot background
        if (slotBg != null) {
            batch.setColor(Color.WHITE);
            batch.draw(slotBg, x, y, w, h);
        }

        // Spell icon
        if (spell != null && spell.getIcon() != null) {
            batch.setColor(Color.WHITE);
            batch.draw(spell.getIcon(), x + 2, y + 2, w - 4, h - 4);
        }

        // Keybind label — bottom-left corner, computed live from current binding
        if (inputBindings != null && boundAction != null) {
            String label = inputBindings.getDisplayName(boundAction);
            if (label != null) {
                font.setColor(Color.DARK_GRAY);
                font.draw(batch, label, x, y);
                font.setColor(Color.WHITE);
            }
        }
    }

    @Override
    public boolean onMouseDown(float mx, float my) {
        if (!visible || !contains(mx, my)) return false;
        if (isDragSource && spell != null && dragListener != null) {
            dragListener.onDragStart(spell, this);
            return true;
        }
        return contains(mx, my);
    }

    @Override
    public boolean onMouseUp(float mx, float my) {
        return false;
    }
}

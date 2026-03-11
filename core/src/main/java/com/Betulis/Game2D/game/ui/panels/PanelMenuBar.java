package com.Betulis.Game2D.game.ui.panels;

import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.ui.core.UIPanel;
import com.Betulis.Game2D.game.ui.core.UIWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * A row of small buttons that each toggle a UI panel — equivalent to pressing
 * the panel's keybind. Positioned to the right of the mouse spell bar.
 */
public class PanelMenuBar extends UIWidget {

    private static final float BTN_W = 44f;
    private static final float BTN_H = 40f;
    private static final float BTN_GAP = 2f;

    private static final Color BTN_NORMAL  = new Color(0.15f, 0.15f, 0.30f, 0.92f);
    private static final Color BTN_HOVER   = new Color(0.25f, 0.25f, 0.50f, 0.95f);
    private static final Color BTN_BORDER  = new Color(0.55f, 0.55f, 0.80f, 1f);

    private final List<Entry> entries = new ArrayList<>();
    private Texture pixel;
    private InputBindings input;

    private static class Entry {
        String label;
        InputBindings.Action boundAction;
        UIPanel target;
        float x, y, w, h;

        boolean contains(float mx, float my) {
            return mx >= x && mx <= x + w && my >= y && my <= y + h;
        }
    }

    /**
     * @param rightEdgeX right edge of this bar (it grows leftward from here)
     * @param y          bottom y, matching the spell bar row
     * @param pixel      white 1×1 texture for tinted rect drawing
     */
    public PanelMenuBar(float rightEdgeX, float y, Texture pixel, InputBindings input) {
        this.pixel = pixel;
        this.input = input;
        this.y = y;
        this.h = BTN_H;
        this.visible = true;
        // x and w are set once buttons are added
    }

    /** Call this after construction for each panel button, left-to-right order. */
    public void addButton(String label, InputBindings.Action action, UIPanel target) {
        Entry e = new Entry();
        e.label       = label;
        e.boundAction = action;
        e.target      = target;
        e.w = BTN_W;
        e.h = BTN_H;
        entries.add(e);
        rebuildLayout();
    }

    private void rebuildLayout() {
        // Total width grows right from x
        this.w = entries.size() * (BTN_W + BTN_GAP) - BTN_GAP;
        // x stays at whatever was set externally; layout buttons left-to-right
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).x = x + i * (BTN_W + BTN_GAP);
            entries.get(i).y = y;
        }
    }

    /** Finalize position — call after all buttons are added. */
    public void setRightEdge(float rightEdgeX) {
        this.x = rightEdgeX - w;
        rebuildLayout();
    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        if (!visible || pixel == null) return;

        for (Entry e : entries) {
            boolean open = e.target != null && e.target.isVisible();

            // Button background — brighter when panel is open
            batch.setColor(open ? BTN_HOVER : BTN_NORMAL);
            batch.draw(pixel, e.x, e.y, e.w, e.h);

            // 1px border
            batch.setColor(BTN_BORDER);
            batch.draw(pixel, e.x,            e.y,            e.w, 1);
            batch.draw(pixel, e.x,            e.y + e.h - 1,  e.w, 1);
            batch.draw(pixel, e.x,            e.y,            1,   e.h);
            batch.draw(pixel, e.x + e.w - 1,  e.y,            1,   e.h);

            // Label centred
            batch.setColor(Color.WHITE);
            font.setColor(Color.WHITE);
            font.draw(batch, e.label, e.x + 4, e.y + 26);

            // Keybind hint bottom-right in yellow
            font.setColor(Color.YELLOW);
            font.draw(batch, input.getDisplayName(e.boundAction), e.x + e.w - 10, e.y + 10);
            font.setColor(Color.WHITE);
        }
    }

    @Override
    public boolean onClick(float mx, float my) {
        if (!visible) return false;
        for (Entry e : entries) {
            if (e.contains(mx, my)) {
                if (e.target != null) e.target.toggle();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMouseDown(float mx, float my) {
        return onClick(mx, my);
    }
}

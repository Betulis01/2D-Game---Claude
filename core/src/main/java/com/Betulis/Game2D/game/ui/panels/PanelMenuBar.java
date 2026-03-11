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

    private static final Color HIGHLIGHT = new Color(0.6f, 0.8f, 1f, 1f);

    private final List<Entry> entries = new ArrayList<>();
    private InputBindings input;

    private static class Entry {
        String label;
        InputBindings.Action boundAction;
        UIPanel target;
        Texture tex;
        float x, y, w, h;

        boolean contains(float mx, float my) {
            return mx >= x && mx <= x + w && my >= y && my <= y + h;
        }
    }

    /**
     * @param rightEdgeX right edge of this bar (it grows leftward from here)
     * @param y          bottom y, matching the spell bar row
     */
    public PanelMenuBar(float rightEdgeX, float y, InputBindings input) {
        this.input = input;
        this.y = y;
        this.h = BTN_H;
        this.visible = true;
        // x and w are set once buttons are added
    }

    /** Call this after construction for each panel button, left-to-right order. */
    public void addButton(String label, InputBindings.Action action, UIPanel target, Texture tex) {
        Entry e = new Entry();
        e.label       = label;
        e.boundAction = action;
        e.target      = target;
        e.tex         = tex;
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
        if (!visible) return;

        for (Entry e : entries) {
            boolean open = e.target != null && e.target.isVisible();

            // Button background — highlighted when panel is open
            batch.setColor(open ? HIGHLIGHT : Color.WHITE);
            batch.draw(e.tex, e.x, e.y, e.w, e.h);
            batch.setColor(Color.WHITE);

            // Keybind hint bottom-right in yellow
            font.setColor(Color.DARK_GRAY);
            font.draw(batch, input.getDisplayName(e.boundAction), e.x, e.y);
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

package com.Betulis.Game2D.game.ui.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class UIWidget {
    protected float x, y, w, h;
    protected boolean visible = true;

    public abstract void render(SpriteBatch batch, BitmapFont font);

    public void update(float dt) {}

    public boolean contains(float mx, float my) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    /** Returns true if the click was consumed. */
    public boolean onClick(float mx, float my) { return false; }
    public boolean onMouseDown(float mx, float my) { return false; }
    public boolean onMouseUp(float mx, float my) { return false; }

    public void setPosition(float x, float y) { this.x = x; this.y = y; }
    public void setSize(float w, float h) { this.w = w; this.h = h; }
    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getW() { return w; }
    public float getH() { return h; }
    public float getRightEdgeX() { return x + w; }
}

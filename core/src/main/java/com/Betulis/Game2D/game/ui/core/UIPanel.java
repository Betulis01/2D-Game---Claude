package com.Betulis.Game2D.game.ui.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class UIPanel extends UIWidget {
    protected String title;
    protected List<UIWidget> children = new ArrayList<>();
    protected Texture background;
    protected Texture panelBg; 

    public UIPanel(String title, float x, float y, float w, float h) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.visible = false; // panels start hidden
    }

    protected void loadPanelBg(String path) {
        if (Gdx.files.local(path).exists()) {
            panelBg = new Texture(Gdx.files.local(path));
            this.w = panelBg.getWidth();
            this.h = panelBg.getHeight();
        }
    }

    public void toggle() { visible = !visible; }

    public void add(UIWidget widget) { children.add(widget); }

    @Override
    public void update(float dt) {
        if (!visible) return;
        for (UIWidget child : children) child.update(dt);
    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        if (!visible) return;

        // Panel background
        if (panelBg != null) {
            batch.setColor(Color.WHITE);
            batch.draw(panelBg, x, y);
        } 

        // Title text
        font.setColor(Color.ORANGE);
        font.draw(batch, title, x + 4, y + h + 15);

        // Children
        for (UIWidget child : children) child.render(batch, font);
    }

    @Override
    public boolean onClick(float mx, float my) {
        if (!visible || !contains(mx, my)) return false;
        for (UIWidget child : children) {
            if (child.onClick(mx, my)) return true;
        }
        return true; // consume clicks on panel even if no child handles it
    }

    @Override
    public boolean onMouseDown(float mx, float my) {
        if (!visible || !contains(mx, my)) return false;
        for (UIWidget child : children) {
            if (child.onMouseDown(mx, my)) return true;
        }
        return true;
    }

    @Override
    public boolean onMouseUp(float mx, float my) {
        if (!visible) return false;
        for (UIWidget child : children) {
            if (child.onMouseUp(mx, my)) return true;
        }
        return contains(mx, my);
    }

    // Injected by UIManager after init
    public static Texture panelPixel;
}

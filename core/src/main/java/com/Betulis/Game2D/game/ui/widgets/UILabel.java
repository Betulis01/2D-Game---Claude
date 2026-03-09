package com.Betulis.Game2D.game.ui.widgets;

import com.Betulis.Game2D.game.ui.core.UIWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UILabel extends UIWidget {
    private String text;
    private Color color;

    public UILabel(String text, float x, float y) {
        this(text, x, y, Color.WHITE);
    }

    public UILabel(String text, float x, float y, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.w = 100;
        this.h = 14;
    }

    public void setText(String text) { this.text = text; }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        if (!visible) return;
        font.setColor(color);
        font.draw(batch, text, x, y);
        font.setColor(Color.WHITE);
    }
}

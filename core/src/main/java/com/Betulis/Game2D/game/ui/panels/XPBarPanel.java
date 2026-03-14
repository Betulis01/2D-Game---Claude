package com.Betulis.Game2D.game.ui.panels;

import com.Betulis.Game2D.game.components.stats.PlayerXP;
import com.Betulis.Game2D.game.ui.core.UIWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class XPBarPanel extends UIWidget {
    private static final int SEGMENTS = 6;
    private static final float SEG_W = 20f;
    private static final float SEG_H = 5f;
    private static final float SEG_GAP = 2f;

    private final Texture xpFull;
    private final Texture xpEmpty;
    private PlayerXP playerXP;

    public XPBarPanel(float screenW, Texture xpFull, Texture xpEmpty) {
        this.xpFull = xpFull;
        this.xpEmpty = xpEmpty;
        this.w = SEGMENTS * SEG_W + (SEGMENTS - 1) * SEG_GAP;
        this.h = SEG_H;
        this.x = screenW / 2f - w / 2f;
        this.y = 0;
        this.visible = true;
    }

    public void setPlayerXP(PlayerXP playerXP) { this.playerXP = playerXP; }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        if (!visible) return;

        int filledSegments = 0;
        if (playerXP != null && playerXP.getMaxXP() > 0) {
            float ratio = playerXP.getCurrentXP() / playerXP.getMaxXP();
            filledSegments = Math.round(ratio * SEGMENTS);
        }

        batch.setColor(Color.WHITE);
        for (int i = 0; i < SEGMENTS; i++) {
            float segX = x + i * (SEG_W + SEG_GAP);
            Texture tex = (i < filledSegments) ? xpFull : xpEmpty;
            batch.draw(tex, segX, y, SEG_W, SEG_H);
        }

        if (playerXP != null && playerXP.getGainTimer() > 0) {
            float alpha  = playerXP.getGainTimer() / 0.8f;
            float gStart = playerXP.getGainStartRatio();
            float gEnd   = playerXP.getGainEndRatio();
            for (int i = 0; i < SEGMENTS; i++) {
                float segStart = (float) i / SEGMENTS;
                float segEnd   = (float)(i + 1) / SEGMENTS;
                if (segEnd > gStart && segStart < gEnd) {
                    batch.setColor(0.35f, 0f, 0.55f, alpha);
                    batch.draw(xpFull, x + i * (SEG_W + SEG_GAP), y, SEG_W, SEG_H);
                }
            }
            batch.setColor(Color.WHITE);
        }
    }
}

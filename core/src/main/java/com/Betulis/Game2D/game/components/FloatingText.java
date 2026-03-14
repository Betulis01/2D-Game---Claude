package com.Betulis.Game2D.game.components;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FloatingText extends Component {
    private final String text;
    private final Color  color;
    private final float  duration;
    private final float  riseSpeed = 20f;
    private float elapsed = 0f;
    private BitmapFont font;

    public FloatingText(String text, Color color, float duration) {
        this.text     = text;
        this.color    = color.cpy();
        this.duration = duration;
        this.font     = new BitmapFont();
    }

    @Override
    public void update(float dt) {
        elapsed += dt;
        if (elapsed >= duration) getGameObject().destroy();
    }

    @Override
    public void render(SpriteBatch batch) {
        Camera cam = getScene().getCamera();
        float wx = transform.getWorldX();
        float wy = transform.getWorldY() + riseSpeed * elapsed;
        float sx = cam.worldToScreenX(wx);
        float sy = cam.worldToScreenY(wy);
        float alpha = 1f - (elapsed / duration);
        font.setColor(color.r, color.g, color.b, alpha);
        font.draw(batch, text, sx, sy);
        font.setColor(Color.WHITE);
    }

    @Override
    public void onDestroy() {
        if (font != null) font.dispose();
    }
}

package com.Betulis.Game2D.engine.render;

import com.Betulis.Game2D.engine.camera.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * SimpleAnimRenderer with rotation applied at render time.
 * Used by projectile attacks (Fireball, LightningBolt, SlimeSpit).
 */
public class RotatedSpriteRenderer extends SimpleAnimRenderer {

    public RotatedSpriteRenderer(float width, float height) {
        super(width, height);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame = getCurrentFrame();
        if (frame == null) return;
        Camera cam = getScene().getCamera();
        float sx = cam.worldToScreenX(transform.getWorldX());
        float sy = cam.worldToScreenY(transform.getWorldY());
        float sw = getWidth()  * cam.getZoom();
        float sh = getHeight() * cam.getZoom();
        batch.draw(
            frame,
            sx - sw * 0.5f, sy - sh * 0.5f,
            sw * 0.5f, sh * 0.5f,
            sw, sh,
            1f, 1f,
            transform.getWorldRotation()
        );
    }
}

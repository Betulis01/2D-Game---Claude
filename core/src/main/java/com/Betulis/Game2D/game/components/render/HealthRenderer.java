package com.Betulis.Game2D.game.components.render;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.render.SimpleAnimRenderer;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.game.components.combat.CombatState;
import com.Betulis.Game2D.game.components.stats.Health;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class HealthRenderer extends Component {
    private Health health;
    private CombatState combat;
    private SimpleAnimRenderer sprite;

    private float width  = 24f;
    private float height = 6f;
    private float yOffset = -15f;

    private TextureRegion pixel;

    @Override
    public void start() {
        health = gameObject.getComponent(Health.class);
        combat = gameObject.getComponent(CombatState.class);
        sprite = gameObject.getComponent(SimpleAnimRenderer.class);

        if (sprite != null) yOffset = sprite.getHeight() / 2f + yOffset;

        pixel = getScene().getGame().getAssets().getPixel();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (health == null || health.isDead()) return;
        if (combat == null || !combat.isInCombat()) return;

        float ratio = health.getHealth() / health.getMaxHealth();
        ratio = Math.max(0f, Math.min(1f, ratio));

        float worldX = transform.getWorldX();
        float worldY = transform.getWorldY() - yOffset;

        Camera cam = getScene().getCamera();
        float barWidth  = width;
        float barHeight = height;
        float screenX = cam.worldToScreenX(worldX) - barWidth  / 2f;
        float screenY = cam.worldToScreenY(worldY) - barHeight / 2f;

        batch.setColor(Color.DARK_GRAY);
        batch.draw(pixel, screenX, screenY, barWidth, barHeight);

        batch.setColor(Color.FIREBRICK);
        batch.draw(pixel, screenX, screenY, barWidth * ratio, barHeight);

        batch.setColor(Color.WHITE);
    }
}

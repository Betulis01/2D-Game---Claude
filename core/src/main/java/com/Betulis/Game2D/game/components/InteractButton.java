package com.Betulis.Game2D.game.components;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.Transform;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InteractButton extends Component {

    private final Interactable source;
    private Texture texture;

    public InteractButton(Interactable source) {
        this.source = source;
    }

    @Override
    public void start() {
        if (Gdx.files.local("assets/ui/interact_button.png").exists()) {
            texture = new Texture(Gdx.files.local("assets/ui/interact_button.png"));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!source.isInRange() || texture == null) return;
        Transform sourceTransform = source.getGameObject().getTransform();
        Camera cam = getScene().getCamera();
        float zoom = cam.getZoom();
        float sx = cam.worldToScreenX(sourceTransform.getWorldX());
        float sy = cam.worldToScreenY(sourceTransform.getWorldY());
        float size = 16f * zoom;
        batch.setColor(Color.WHITE);
        batch.draw(texture, sx - size * 0.5f, sy + size * 0.5f, size, size);
    }

    @Override
    public void onDestroy() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }
}

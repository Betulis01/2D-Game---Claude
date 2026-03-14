package com.Betulis.Game2D.engine.render;

import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteRenderer extends Component {
    private AnimationDirector director;
    private AnimationUpdater updater;

    private final float width;
    private final float height;
    private int   sortLayer   = 2;
    private float sortYOffset;

    public SpriteRenderer(float width, float height) {
        this.width = width;
        this.height = height;
        this.sortYOffset = height / 2f;
    }

    @Override
    public void start() {
        director = getGameObject().getComponent(AnimationDirector.class);
        updater = getGameObject().getComponent(AnimationUpdater.class);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (director == null && updater == null) return;
        
        AnimationClip clip = director.getCurrentClip();
        if (clip == null) return;  

        int index = updater.getFrameIndex();
        TextureRegion img = clip.getFrame(index);
        if (img == null) return;

        Camera cam = getScene().getCamera();
        float zoom = cam.getZoom();

        float screenX = cam.worldToScreenX(transform.getWorldX());
        float screenY = cam.worldToScreenY(transform.getWorldY());

        float sizeW = width * zoom;
        float sizeH = height * zoom;
        batch.draw(
            img,
            screenX - sizeW * 0.5f,
            screenY - sizeH * 0.5f,
            sizeW,
            sizeH
        );
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int   getSortLayer()               { return sortLayer; }
    public void  setSortLayer(int layer)      { this.sortLayer = layer; }
    public float getSortYOffset()             { return sortYOffset; }
    public void  setSortYOffset(float offset) { this.sortYOffset = offset; }
}

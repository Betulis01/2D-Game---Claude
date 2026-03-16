package com.Betulis.Game2D.engine.render;

import com.Betulis.Game2D.engine.animation.CharacterDirection;
import com.Betulis.Game2D.engine.animation.CharacterState;
import com.Betulis.Game2D.engine.animation.DirectionalAnimSet;
import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.Component;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Multi-layer directional sprite renderer for the player.
 * Layer 0 is the body; equipment layers start at index 1.
 * stateTime resets only on state change, not direction change.
 */
public class LayeredSpriteRenderer extends Component {

    private final List<Map<CharacterState, DirectionalAnimSet>> layers = new ArrayList<>();
    private CharacterState    currentState = CharacterState.IDLE;
    private CharacterDirection currentDir  = CharacterDirection.S;
    private float stateTime = 0;

    private final float width;
    private final float height;
    private int   sortLayer   = 2;
    private float sortYOffset;

    public LayeredSpriteRenderer(float width, float height) {
        this.width       = width;
        this.height      = height;
        this.sortYOffset = height / 2f;
    }

    public void addLayer(Map<CharacterState, DirectionalAnimSet> layer) {
        layers.add(layer);
    }

    /** Removes all layers at index and above. */
    public void removeLayersFromIndex(int index) {
        while (layers.size() > index) {
            layers.remove(layers.size() - 1);
        }
    }

    /** Resets stateTime only when the state changes; direction changes are seamless. */
    public void setStateDir(CharacterState state, CharacterDirection dir) {
        if (this.currentState != state) {
            this.stateTime = 0;
        }
        this.currentState = state;
        this.currentDir   = dir;
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (layers.isEmpty()) return;
        Camera cam = getScene().getCamera();
        float sx = cam.worldToScreenX(transform.getWorldX());
        float sy = cam.worldToScreenY(transform.getWorldY());
        float sw = width  * cam.getZoom();
        float sh = height * cam.getZoom();

        for (Map<CharacterState, DirectionalAnimSet> layer : layers) {
            DirectionalAnimSet animSet = layer.get(currentState);
            if (animSet == null) animSet = layer.get(CharacterState.IDLE);
            if (animSet == null) continue;
            TextureRegion frame = animSet.getKeyFrame(currentDir, stateTime);
            if (frame == null) continue;
            batch.draw(frame, sx - sw * 0.5f, sy - sh * 0.5f, sw, sh);
        }
    }

    public float getWidth()                   { return width; }
    public float getHeight()                  { return height; }
    public int   getSortLayer()               { return sortLayer; }
    public void  setSortLayer(int layer)      { this.sortLayer = layer; }
    public float getSortYOffset()             { return sortYOffset; }
    public void  setSortYOffset(float offset) { this.sortYOffset = offset; }
}

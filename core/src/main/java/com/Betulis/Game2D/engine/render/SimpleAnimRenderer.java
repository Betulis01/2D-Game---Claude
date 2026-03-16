package com.Betulis.Game2D.engine.render;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * Replaces SpriteRenderer + AnimationDirector + AnimationUpdater for non-directional entities.
 * Uses LibGDX's built-in Animation<TextureRegion>.
 */
public class SimpleAnimRenderer extends Component {

    private final Map<String, Animation<TextureRegion>> clips = new HashMap<>();
    private Animation<TextureRegion> currentAnim;
    private float stateTime;
    private boolean loop = true;

    private final float width;
    private final float height;
    private int   sortLayer   = 2;
    private float sortYOffset;

    public SimpleAnimRenderer(float width, float height) {
        this.width       = width;
        this.height      = height;
        this.sortYOffset = height / 2f;
    }

    // ── Static helpers ──────────────────────────────────────────────

    /** Slices all columns of a spritesheet row into an Animation. */
    public static Animation<TextureRegion> clipFromSheet(Texture tex, int frameW, int frameH,
                                                          int row, float frameDuration) {
        int cols = tex.getWidth() / frameW;
        TextureRegion[] frames = new TextureRegion[cols];
        for (int i = 0; i < cols; i++) {
            frames[i] = new TextureRegion(tex, i * frameW, row * frameH, frameW, frameH);
        }
        return new Animation<>(frameDuration, frames);
    }

    /** Slices a fixed number of frames from a spritesheet row. */
    public static Animation<TextureRegion> clipFromSheet(Texture tex, int frameW, int frameH,
                                                          int row, float frameDuration, int frameCount) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = new TextureRegion(tex, i * frameW, row * frameH, frameW, frameH);
        }
        return new Animation<>(frameDuration, frames);
    }

    /**
     * Builds an Animation from indexed atlas regions (e.g. prefix="_fly" → _fly index 0,1,2...).
     * Returns null if the atlas is null or no matching regions are found.
     */
    public static Animation<TextureRegion> clipFromAtlas(TextureAtlas atlas, String regionName,
                                                          float frameDuration) {
        if (atlas == null) return null;
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(regionName);
        if (regions.isEmpty()) return null;
        TextureRegion[] frames = new TextureRegion[regions.size];
        for (int i = 0; i < regions.size; i++) frames[i] = regions.get(i);
        return new Animation<>(frameDuration, frames);
    }

    // ── API ─────────────────────────────────────────────────────────

    public void addClip(String name, Animation<TextureRegion> anim) {
        if (anim == null) return;
        clips.put(name, anim);
        if (currentAnim == null) {
            currentAnim = anim;
            loop = true;
        }
    }

    public void play(String name) {
        play(name, true);
    }

    public void play(String name, boolean loop) {
        Animation<TextureRegion> next = clips.get(name);
        if (next == null) return;
        if (next == currentAnim && this.loop == loop && !isFinished()) return;
        this.currentAnim = next;
        this.loop        = loop;
        this.stateTime   = 0;
    }

    public boolean isFinished() {
        if (currentAnim == null) return true;
        return !loop && currentAnim.isAnimationFinished(stateTime);
    }

    public boolean isLooping() {
        return loop;
    }

    // ── Component lifecycle ─────────────────────────────────────────

    @Override
    public void update(float dt) {
        stateTime += dt;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame = getCurrentFrame();
        if (frame == null) return;
        Camera cam = getScene().getCamera();
        float sx = cam.worldToScreenX(transform.getWorldX());
        float sy = cam.worldToScreenY(transform.getWorldY());
        float sw = width  * cam.getZoom();
        float sh = height * cam.getZoom();
        batch.draw(frame, sx - sw * 0.5f, sy - sh * 0.5f, sw, sh);
    }

    protected TextureRegion getCurrentFrame() {
        if (currentAnim == null) return null;
        currentAnim.setPlayMode(loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
        return currentAnim.getKeyFrame(stateTime);
    }

    // ── Sort accessors (same interface as old SpriteRenderer) ────────

    public float getWidth()                       { return width; }
    public float getHeight()                      { return height; }
    public int   getSortLayer()                   { return sortLayer; }
    public void  setSortLayer(int layer)          { this.sortLayer = layer; }
    public float getSortYOffset()                 { return sortYOffset; }
    public void  setSortYOffset(float offset)     { this.sortYOffset = offset; }
}

package com.Betulis.Game2D.engine.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.EnumMap;
import java.util.Map;

/**
 * Loads one animation state (e.g. "idle" or "walk") for all 8 directions from a TextureAtlas.
 * The 5 drawn directions (N, NE, E, SE, S) are loaded from atlas regions named "_animTag_suffix".
 * The 3 mirrored directions (NW, W, SW) are pre-baked as flipped copies at construction time.
 */
public class DirectionalAnimSet {

    private final Map<CharacterDirection, Animation<TextureRegion>> anims =
            new EnumMap<>(CharacterDirection.class);

    public DirectionalAnimSet(TextureAtlas atlas, String animTag, float frameDuration) {
        if (atlas == null) return;

        CharacterDirection[] drawn = { CharacterDirection.N, CharacterDirection.NE,
                CharacterDirection.E, CharacterDirection.SE, CharacterDirection.S };

        CharacterDirection fallback = null;

        for (CharacterDirection dir : drawn) {
            String regionName = "_" + animTag + "_" + dir.getAtlasSuffix();
            Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(regionName);
            if (regions.isEmpty()) {
                // try South as fallback
                regions = atlas.findRegions("_" + animTag + "_s");
            }
            if (regions.isEmpty()) continue;

            TextureRegion[] frames = toFrameArray(regions);
            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(Animation.PlayMode.LOOP);
            anims.put(dir, anim);
            if (fallback == null) fallback = dir;
        }

        // Pre-bake horizontally-flipped copies for left-side directions
        bakeFlipped(CharacterDirection.NE, CharacterDirection.NW);
        bakeFlipped(CharacterDirection.E,  CharacterDirection.W);
        bakeFlipped(CharacterDirection.SE, CharacterDirection.SW);
    }

    private void bakeFlipped(CharacterDirection source, CharacterDirection target) {
        Animation<TextureRegion> src = anims.get(source);
        if (src == null) return;
        TextureRegion[] srcFrames = src.getKeyFrames();
        TextureRegion[] flipped = new TextureRegion[srcFrames.length];
        for (int i = 0; i < srcFrames.length; i++) {
            flipped[i] = new TextureRegion(srcFrames[i]);
            flipped[i].flip(true, false);
        }
        Animation<TextureRegion> anim = new Animation<>(src.getFrameDuration(), flipped);
        anim.setPlayMode(Animation.PlayMode.LOOP);
        anims.put(target, anim);
    }

    private TextureRegion[] toFrameArray(Array<TextureAtlas.AtlasRegion> regions) {
        TextureRegion[] frames = new TextureRegion[regions.size];
        for (int i = 0; i < regions.size; i++) frames[i] = regions.get(i);
        return frames;
    }

    public TextureRegion getKeyFrame(CharacterDirection dir, float stateTime) {
        Animation<TextureRegion> anim = anims.get(dir);
        if (anim == null) anim = anims.get(CharacterDirection.S);
        if (anim == null) return null;
        return anim.getKeyFrame(stateTime, true);
    }
}

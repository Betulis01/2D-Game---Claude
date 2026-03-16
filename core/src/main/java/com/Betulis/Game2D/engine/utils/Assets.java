package com.Betulis.Game2D.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private final AssetManager manager = new AssetManager();

    // Still-existing sprite sheets / textures
    public static final String orc_sheet   = "player/orc8.png";
    public static final String small_sword = "items/weapons/small-sword.png";
    public static final String blue_ring   = "items/accessories/blue_ring.png";

    // TextureAtlas paths (produced by ./gradlew packAllAtlases)
    public static final String orc_atlas       = "player/atlas/Orc.atlas";
    public static final String fireball_atlas  = "abilities/fireball/atlas/fireball.atlas";
    public static final String lightning_atlas = "abilities/lightning_bolt/atlas/lightning_bolt.atlas";
    public static final String spit_atlas      = "abilities/spit/atlas/spit.atlas";
    public static final String slime_atlas     = "mob/slime/atlas/slime.atlas";

    // Runtime-created 1x1 white texture
    private Texture pixelTex;
    private TextureRegion pixelRegion;

    public void load() {
        manager.load(orc_sheet,   Texture.class);
        manager.load(small_sword, Texture.class);
        manager.load(blue_ring,   Texture.class);

        // Atlases — loaded only if the packed file exists
        loadAtlasIfExists(orc_atlas);
        loadAtlasIfExists(fireball_atlas);
        loadAtlasIfExists(lightning_atlas);
        loadAtlasIfExists(spit_atlas);
        loadAtlasIfExists(slime_atlas);

        manager.finishLoading();

        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(1, 1, 1, 1);
        pm.fill();
        pixelTex = new Texture(pm);
        pm.dispose();

        pixelRegion = new TextureRegion(pixelTex);
    }

    private void loadAtlasIfExists(String path) {
        if (Gdx.files.internal(path).exists()) {
            manager.load(path, TextureAtlas.class);
        }
    }

    public Texture getTexture(String path) {
        return manager.get(path, Texture.class);
    }

    /** Returns the atlas for the given path, or null if it was not loaded. */
    public TextureAtlas getAtlas(String path) {
        if (!manager.isLoaded(path)) return null;
        return manager.get(path, TextureAtlas.class);
    }

    /** A 1x1 white region for drawing colored rectangles via SpriteBatch tinting. */
    public TextureRegion getPixel() {
        return pixelRegion;
    }

    public void dispose() {
        manager.dispose();
        if (pixelTex != null) pixelTex.dispose();
    }
}

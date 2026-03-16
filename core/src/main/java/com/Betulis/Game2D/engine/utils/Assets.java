package com.Betulis.Game2D.engine.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private final AssetManager manager = new AssetManager();

    public static final String orc_sheet = "player/orc8.png";
    public static final String slime_sheet  = "mob/slime.png";
    public static final String slime_death  = "mob/slime/slime_death.png";
    public static final String slime_attack = "mob/slime/slime_attack.png";
    public static final String slime_spit   = "mob/slime/slime_spit.png";
    public static final String fireball_fly = "abilities/fireball/fireball_fly.png";
    public static final String fireball_explode = "abilities/fireball/fireball_explode.png";
    public static final String fireball_nohit = "abilities/fireball/fireball_nohit.png";
    public static final String lightning_bolt = "abilities/lightning_bolt/lightning_bolt.png";
    public static final String small_sword = "items/weapons/small-sword.png";
    public static final String blue_ring   = "items/accessories/blue_ring.png";

    // Runtime-created 1x1 white texture
    private Texture pixelTex;
    private TextureRegion pixelRegion;

    public void load() {
        manager.load(orc_sheet, Texture.class);
        manager.load(slime_sheet,  Texture.class);
        manager.load(slime_death,  Texture.class);
        manager.load(slime_attack, Texture.class);
        manager.load(slime_spit,   Texture.class);
        manager.load(fireball_fly, Texture.class);
        manager.load(fireball_explode, Texture.class);
        manager.load(fireball_nohit, Texture.class);
        manager.load(lightning_bolt, Texture.class);
        manager.load(small_sword, Texture.class);
        manager.load(blue_ring,   Texture.class);

        manager.finishLoading();

        // Create the pixel AFTER OpenGL is available (load() should be called after app start)
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(1, 1, 1, 1);
        pm.fill();
        pixelTex = new Texture(pm);
        pm.dispose();

        pixelRegion = new TextureRegion(pixelTex);
    }

    public Texture getTexture(String path) {
        return manager.get(path, Texture.class);
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

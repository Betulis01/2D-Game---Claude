package com.Betulis.Game2D.game.prefabs.mobs.slime;

import com.Betulis.Game2D.engine.animation.AnimationAutoDespawner;
import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.render.SimpleAnimRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.utils.Assets;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SlimeDeathEffect {

    public static GameObject create(float x, float y, Assets assets) {
        EntityConfig cfg = new ConfigLoader().load("data/config/slime_death.json");
        GameObject effect = new GameObject("SlimeDeath");

        effect.getTransform().setWorldPosition(x, y);

        TextureAtlas atlas = assets.getAtlas(Assets.slime_atlas);
        SimpleAnimRenderer renderer = new SimpleAnimRenderer(cfg.sprite.width, cfg.sprite.height);
        renderer.addClip("die", SimpleAnimRenderer.clipFromAtlas(atlas, "_death", cfg.sprite.frameDuration));
        renderer.play("die", false); // non-looping → AutoDespawner destroys it
        effect.addComponent(renderer);
        effect.addComponent(new AnimationAutoDespawner());

        return effect;
    }
}

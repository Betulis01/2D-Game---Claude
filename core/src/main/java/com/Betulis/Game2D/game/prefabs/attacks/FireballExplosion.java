package com.Betulis.Game2D.game.prefabs.attacks;

import com.Betulis.Game2D.engine.animation.AnimationAutoDespawner;
import com.Betulis.Game2D.engine.audio.AudioPlayer;
import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.render.RotatedSpriteRenderer;
import com.Betulis.Game2D.engine.render.SimpleAnimRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.utils.Assets;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class FireballExplosion {

    public static GameObject create(GameObject owner, Assets assets) {
        EntityConfig cfg = new ConfigLoader().load("data/config/abilities/fireball_explosion.json");
        GameObject effect = new GameObject("FireballExplosion");

        // Parent to owner so the effect stays at the impact point
        effect.getTransform().setParent(owner.getTransform());

        // Animation
        TextureAtlas atlas = assets.getAtlas(Assets.fireball_atlas);
        RotatedSpriteRenderer renderer = new RotatedSpriteRenderer(cfg.sprite.width, cfg.sprite.height);
        renderer.addClip("explode", SimpleAnimRenderer.clipFromAtlas(atlas, "explode", cfg.sprite.frameDuration));
        renderer.play("explode", false); // non-looping → AutoDespawner destroys it
        renderer.setSortLayer(3);
        effect.addComponent(renderer);

        // Sound
        effect.addComponent(new AudioPlayer("fireball_explosion"));

        // Despawn when animation finishes
        effect.addComponent(new AnimationAutoDespawner());

        return effect;
    }
}

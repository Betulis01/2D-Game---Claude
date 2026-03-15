package com.Betulis.Game2D.game.prefabs.attacks;

import com.Betulis.Game2D.engine.animation.AnimationAutoDespawner;
import com.Betulis.Game2D.engine.audio.AudioPlayer;
import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.render.RotatedSpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.badlogic.gdx.graphics.Texture;

public class FireballExplosion {
    public static GameObject create(GameObject owner, Texture asset) {
        EntityConfig cfg = new ConfigLoader().load("data/config/abilities/fireball_explosion.json");
        GameObject effect = new GameObject("FireballExplosion");

        //Transform — parent to owner so the effect follows if it moves
        effect.getTransform().setParent(owner.getTransform());

        //Animation
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, cfg.sprite.width, cfg.sprite.height, cfg.sprite.frames, cfg.sprite.directions);
        AnimationClip explode = new AnimationClip(sheet, cfg.sprite.frameDuration, 0, 0, cfg.sprite.frames - 1, 0);

        AnimationDirector director = new AnimationDirector();
        director.add("explode", explode);
        director.play("explode", false);
        effect.addComponent(director);
        effect.addComponent(new AnimationUpdater());
        RotatedSpriteRenderer rsr = new RotatedSpriteRenderer(cfg.sprite.width, cfg.sprite.height);
        rsr.setSortLayer(3);
        effect.addComponent(rsr);

        //Sound
        effect.addComponent(new AudioPlayer("fireball_explosion"));

        //Despawn
        effect.addComponent(new AnimationAutoDespawner());

        return effect;
    }
}

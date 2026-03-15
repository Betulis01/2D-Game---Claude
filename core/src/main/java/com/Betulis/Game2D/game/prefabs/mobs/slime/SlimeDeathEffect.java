package com.Betulis.Game2D.game.prefabs.mobs.slime;

import com.Betulis.Game2D.engine.animation.AnimationAutoDespawner;
import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.render.SpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.badlogic.gdx.graphics.Texture;

public class SlimeDeathEffect {

    public static GameObject create(float x, float y, Texture asset) {
        EntityConfig cfg = new ConfigLoader().load("data/config/slime_death.json");
        GameObject effect = new GameObject("SlimeDeath");

        effect.getTransform().setWorldPosition(x, y);

        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, cfg.sprite.width, cfg.sprite.height, cfg.sprite.frames, cfg.sprite.directions);
        AnimationClip die = new AnimationClip(sheet, cfg.sprite.frameDuration, 0, 0, cfg.sprite.frames - 1, 0);

        AnimationDirector director = new AnimationDirector();
        director.add("die", die);
        director.play("die", false);
        effect.addComponent(director);
        effect.addComponent(new AnimationUpdater());
        effect.addComponent(new SpriteRenderer(cfg.sprite.width, cfg.sprite.height));
        effect.addComponent(new AnimationAutoDespawner());

        return effect;
    }
}

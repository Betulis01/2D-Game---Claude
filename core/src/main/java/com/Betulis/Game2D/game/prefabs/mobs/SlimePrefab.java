package com.Betulis.Game2D.game.prefabs.mobs;

import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.render.SpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.Betulis.Game2D.game.components.AABB.Hurtbox;
import com.Betulis.Game2D.game.components.ai.SlimeAI;
import com.Betulis.Game2D.game.components.items.LootDropper;
import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.components.animation.SlimeAnimation;
import com.Betulis.Game2D.game.components.combat.CombatState;
import com.Betulis.Game2D.game.components.movement.ChaseMovement;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.WanderMovement;
import com.Betulis.Game2D.game.components.render.HealthRenderer;
import com.Betulis.Game2D.game.components.stats.Health;
import com.badlogic.gdx.graphics.Texture;

public class SlimePrefab {

    public GameObject create(float x, float y, Texture asset) {
        EntityConfig cfg = new ConfigLoader().load("data/config/slime.json");
        GameObject slimeObj = new GameObject("Slime");

        //Transform
        slimeObj.getComponent(Transform.class).setPosition(x, y);

        //Movement
        float wanderSpeed = cfg.stats.moveSpeed * 0.8f;
        float chaseSpeed  = cfg.stats.moveSpeed * 1.2f;
        slimeObj.addComponent(new WanderMovement(wanderSpeed));
        slimeObj.addComponent(new ChaseMovement(cfg.sprite.width, chaseSpeed));
        slimeObj.addComponent(new EntityMover(wanderSpeed));
        slimeObj.addComponent(new SlimeAI());

        //Animation
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, cfg.sprite.width, cfg.sprite.height, cfg.sprite.frames, cfg.sprite.directions);
        AnimationDirector director = new AnimationDirector();
        AnimationClip jump = new AnimationClip(sheet, cfg.sprite.frameDuration, 0, 0, cfg.sprite.frames - 1, 0);
        AnimationClip idle = new AnimationClip(sheet, cfg.sprite.frameDuration, 0, 0, 0, 0);
        director.add("jump", jump);
        director.add("idle", idle);
        slimeObj.addComponent(director);
        slimeObj.addComponent(new AnimationUpdater());
        slimeObj.addComponent(new SpriteRenderer(cfg.sprite.width, cfg.sprite.height));
        slimeObj.addComponent(new SlimeAnimation());

        //Health
        slimeObj.addComponent(new Health(cfg.stats.maxHealth, 0));
        slimeObj.addComponent(new HealthRenderer());

        //Combat
        slimeObj.addComponent(new CombatState());

        //Hurtbox
        slimeObj.addComponent(new Hurtbox(cfg.hurtbox.width, cfg.hurtbox.height, cfg.hurtbox.offsetX, cfg.hurtbox.offsetY));

        //Loot
        slimeObj.addComponent(new LootDropper(Assets.small_sword, "data/config/items/small-sword.json", 0.6f));

        return slimeObj;
    }
}

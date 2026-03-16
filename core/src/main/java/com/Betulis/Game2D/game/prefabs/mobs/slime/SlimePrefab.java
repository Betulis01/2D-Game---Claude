package com.Betulis.Game2D.game.prefabs.mobs.slime;

import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.render.SimpleAnimRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.components.AABB.Hurtbox;
import com.Betulis.Game2D.game.components.ai.SlimeAI;
import com.Betulis.Game2D.game.components.animation.SlimeAnimation;
import com.Betulis.Game2D.game.components.combat.AttackSpawner;
import com.Betulis.Game2D.game.components.combat.CombatState;
import com.Betulis.Game2D.game.components.combat.DeathEffectFactory;
import com.Betulis.Game2D.game.components.combat.DeathEffects;
import com.Betulis.Game2D.game.components.combat.ReactOnHit;
import com.Betulis.Game2D.game.components.items.LootDropper;
import com.Betulis.Game2D.game.components.movement.ChaseMovement;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.WanderMovement;
import com.Betulis.Game2D.game.components.render.HealthRenderer;
import com.Betulis.Game2D.game.components.stats.Health;
import com.Betulis.Game2D.game.components.stats.XPReward;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SlimePrefab {

    public GameObject create(float x, float y, Assets assets) {
        EntityConfig cfg = new ConfigLoader().load("data/config/slime.json");
        GameObject slimeObj = new GameObject("Slime");

        // Transform
        slimeObj.getComponent(Transform.class).setPosition(x, y);

        // Movement
        float wanderSpeed = cfg.stats.moveSpeed * 0.8f;
        float chaseSpeed  = cfg.stats.moveSpeed * 1.2f;
        float attackRange = cfg.stats.attackRange * 1.2f;
        slimeObj.addComponent(new WanderMovement(wanderSpeed));
        slimeObj.addComponent(new ChaseMovement(cfg.sprite.width, chaseSpeed, attackRange));
        slimeObj.addComponent(new EntityMover(wanderSpeed));

        EntityConfig spitCfg = new ConfigLoader().load("data/config/slime_spit.json");
        AttackSpawner attackSpawner = new AttackSpawner();
        attackSpawner.setAttack(spitCfg);
        slimeObj.addComponent(attackSpawner);
        SlimeAI slimeAI = new SlimeAI(attackSpawner);
        slimeObj.addComponent(slimeAI);

        // Animation
        TextureAtlas slimeAtlas = assets.getAtlas(Assets.slime_atlas);
        SimpleAnimRenderer renderer = new SimpleAnimRenderer(cfg.sprite.width, cfg.sprite.height);
        renderer.addClip("jump", SimpleAnimRenderer.clipFromAtlas(slimeAtlas, "_walk", cfg.sprite.frameDuration));
        renderer.addClip("idle", SimpleAnimRenderer.clipFromAtlas(slimeAtlas, "_idle", cfg.sprite.frameDuration));
        slimeObj.addComponent(renderer);
        slimeObj.addComponent(new SlimeAnimation());

        // Health
        slimeObj.addComponent(new Health(cfg.stats.maxHealth, 0));
        slimeObj.addComponent(new HealthRenderer());

        // Combat
        slimeObj.addComponent(new CombatState());
        ReactOnHit react = new ReactOnHit();
        react.add(ctx -> slimeAI.aggro(ctx.attacker.getTransform()));
        slimeObj.addComponent(react);

        // Death Effects
        slimeObj.addComponent(new DeathEffects(new DeathEffectFactory(assets).build(cfg.onDeathEffects)));

        // Hurtbox
        slimeObj.addComponent(new Hurtbox(cfg.hurtbox.width, cfg.hurtbox.height,
                cfg.hurtbox.offsetX, cfg.hurtbox.offsetY));

        // XP Reward
        slimeObj.addComponent(new XPReward(cfg.stats.xpReward));

        // Loot
        slimeObj.addComponent(new LootDropper(Assets.small_sword, "data/config/items/small-sword.json", 0.4f));
        slimeObj.addComponent(new LootDropper(Assets.blue_ring,   "data/config/items/blue-ring.json",   0.4f));

        return slimeObj;
    }
}

package com.Betulis.Game2D.game.prefabs.attacks;

import com.Betulis.Game2D.engine.audio.AudioPlayer;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.render.RotatedSpriteRenderer;
import com.Betulis.Game2D.engine.render.SimpleAnimRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.components.AABB.AttackOutsideMapDespawner;
import com.Betulis.Game2D.game.components.AABB.Hitbox;
import com.Betulis.Game2D.game.components.combat.AttackDurationDespawner;
import com.Betulis.Game2D.game.components.combat.DamageOnHit;
import com.Betulis.Game2D.game.components.combat.HitEffect;
import com.Betulis.Game2D.game.components.combat.HitEffectFactory;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.Projectile;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.List;

public final class AttackPrefabs {

    public static GameObject createFireball(EntityConfig cfg, GameObject owner, Vector2 dir, Assets assets) {
        GameObject attack = new GameObject("Fireball");

        // Transform
        Transform ot = owner.getTransform();
        attack.getTransform().setWorldPosition(ot.getWorldX(), ot.getWorldY());
        float angle = (float) Math.toDegrees(Math.atan2(dir.y, dir.x));
        attack.getTransform().setRotation(angle);

        // Animation
        TextureAtlas atlas = assets.getAtlas(Assets.fireball_atlas);
        RotatedSpriteRenderer renderer = new RotatedSpriteRenderer(cfg.sprite.width, cfg.sprite.height);
        renderer.addClip("fly", SimpleAnimRenderer.clipFromAtlas(atlas, "_fly", cfg.sprite.frameDuration));
        renderer.play("fly");
        renderer.setSortLayer(3);
        attack.addComponent(renderer);

        // Despawner
        attack.addComponent(new AttackOutsideMapDespawner());
        attack.addComponent(new AttackDurationDespawner(cfg.stats.duration));

        // Movement
        attack.addComponent(new Projectile(dir));
        attack.addComponent(new EntityMover(cfg.stats.moveSpeed));

        // Hitbox
        EntityConfig.Hitbox hi = cfg.hitbox;
        attack.addComponent(new Hitbox(hi.width, hi.height, hi.offsetX, hi.offsetY));

        // Damage
        List<HitEffect> effects = new HitEffectFactory(assets).build(cfg.onHitEffects);
        attack.addComponent(new DamageOnHit(owner, cfg.stats.damage, cfg.id, effects));

        // Sounds
        if (cfg.spawnSounds != null) {
            for (String id : cfg.spawnSounds) {
                attack.addComponent(new AudioPlayer(id));
            }
        }

        return attack;
    }

    public static GameObject createSlimeSpit(EntityConfig cfg, GameObject owner, Vector2 dir, Assets assets) {
        GameObject attack = new GameObject("SlimeSpit");

        // Transform
        Transform ot = owner.getTransform();
        attack.getTransform().setWorldPosition(ot.getWorldX(), ot.getWorldY());
        float angle = (float) Math.toDegrees(Math.atan2(dir.y, dir.x));
        attack.getTransform().setRotation(angle);

        // Animation
        TextureAtlas atlas = assets.getAtlas(Assets.spit_atlas);
        RotatedSpriteRenderer renderer = new RotatedSpriteRenderer(cfg.sprite.width, cfg.sprite.height);
        renderer.addClip("fly", SimpleAnimRenderer.clipFromAtlas(atlas, "_fly", cfg.sprite.frameDuration));
        renderer.play("fly");
        renderer.setSortLayer(3);
        attack.addComponent(renderer);

        // Despawner
        attack.addComponent(new AttackOutsideMapDespawner());
        attack.addComponent(new AttackDurationDespawner(cfg.stats.duration));

        // Movement
        attack.addComponent(new Projectile(dir));
        attack.addComponent(new EntityMover(cfg.stats.moveSpeed));

        // Hitbox
        EntityConfig.Hitbox hi = cfg.hitbox;
        attack.addComponent(new Hitbox(hi.width, hi.height, hi.offsetX, hi.offsetY));

        // Damage
        List<HitEffect> effects = new HitEffectFactory(assets).build(cfg.onHitEffects);
        attack.addComponent(new DamageOnHit(owner, cfg.stats.damage, cfg.id, effects));

        return attack;
    }

    public static GameObject createLightningBolt(EntityConfig cfg, GameObject owner, Vector2 dir, Assets assets) {
        GameObject attack = new GameObject("LightningBolt");

        // Transform
        Transform ot = owner.getTransform();
        attack.getTransform().setWorldPosition(ot.getWorldX(), ot.getWorldY());
        float angle = (float) Math.toDegrees(Math.atan2(dir.y, dir.x));
        attack.getTransform().setRotation(angle);

        // Animation
        TextureAtlas atlas = assets.getAtlas(Assets.lightning_atlas);
        RotatedSpriteRenderer renderer = new RotatedSpriteRenderer(cfg.sprite.width, cfg.sprite.height);
        renderer.addClip("fly", SimpleAnimRenderer.clipFromAtlas(atlas, "_fly", cfg.sprite.frameDuration));
        renderer.play("fly");
        renderer.setSortLayer(3);
        attack.addComponent(renderer);

        // Despawner
        attack.addComponent(new AttackOutsideMapDespawner());
        attack.addComponent(new AttackDurationDespawner(cfg.stats.duration));

        // Movement
        attack.addComponent(new Projectile(dir));
        attack.addComponent(new EntityMover(cfg.stats.moveSpeed));

        // Hitbox
        EntityConfig.Hitbox hi = cfg.hitbox;
        attack.addComponent(new Hitbox(hi.width, hi.height, hi.offsetX, hi.offsetY));

        // Damage
        List<HitEffect> effects = new HitEffectFactory(assets).build(cfg.onHitEffects);
        attack.addComponent(new DamageOnHit(owner, cfg.stats.damage, cfg.id, effects));

        return attack;
    }
}

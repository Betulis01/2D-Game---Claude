package com.Betulis.Game2D.game.prefabs.attacks;

import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.render.RotatedSpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.Betulis.Game2D.game.components.AABB.AttackOutsideMapDespawner;
import com.Betulis.Game2D.game.components.AABB.Hitbox;
import com.Betulis.Game2D.game.components.combat.AttackDurationDespawner;
import com.Betulis.Game2D.game.components.combat.DamageOnHit;
import com.Betulis.Game2D.game.components.combat.HitEffect;
import com.Betulis.Game2D.game.components.combat.HitEffectFactory;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.Projectile;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public final class AttackPrefabs {

    public static GameObject createFireball(EntityConfig cfg, GameObject owner, Vector2 dir, Assets assets) {
        GameObject attack = new GameObject("Fireball");

        //Transform
        Transform ot = owner.getTransform();
        attack.getTransform().setWorldPosition(ot.getWorldX(), ot.getWorldY());
        float angle = (float) Math.toDegrees(Math.atan2(dir.y, dir.x));
        attack.getTransform().setRotation(angle);

        //Animation
        Texture asset = assets.getTexture(Assets.fireball_fly);
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, cfg.sprite.width, cfg.sprite.height, cfg.sprite.frames, cfg.sprite.directions);
        AnimationClip fly = new AnimationClip(sheet, cfg.sprite.frameDuration, 0, 0, cfg.sprite.frames - 1, 0);

        AnimationDirector director = new AnimationDirector();
        director.add("fly", fly);
        attack.addComponent(director);
        attack.addComponent(new AnimationUpdater());
        RotatedSpriteRenderer fireball_rsr = new RotatedSpriteRenderer(cfg.sprite.width, cfg.sprite.height);
        fireball_rsr.setSortLayer(3);
        attack.addComponent(fireball_rsr);

        //Despawner
        attack.addComponent(new AttackOutsideMapDespawner());
        attack.addComponent(new AttackDurationDespawner(cfg.stats.duration));

        //Movement
        attack.addComponent(new Projectile(dir));
        attack.addComponent(new EntityMover(cfg.stats.moveSpeed));

        //Hitbox
        EntityConfig.Hitbox hi = cfg.hitbox;
        attack.addComponent(new Hitbox(hi.width, hi.height, hi.offsetX, hi.offsetY));

        //Damage
        List<HitEffect> effects = new HitEffectFactory(assets).build(cfg.onHitEffects);
        attack.addComponent(new DamageOnHit(owner, cfg.stats.damage, cfg.id, effects));

        return attack;
    }

    public static GameObject createLightningBolt(EntityConfig cfg, GameObject owner, Vector2 dir, Assets assets) {
        GameObject attack = new GameObject("LightningBolt");

        //Transform
        Transform ot = owner.getTransform();
        attack.getTransform().setWorldPosition(ot.getWorldX(), ot.getWorldY());
        float angle = (float) Math.toDegrees(Math.atan2(dir.y, dir.x));
        attack.getTransform().setRotation(angle);

        //Animation
        Texture asset = assets.getTexture(Assets.lightning_bolt);
        SpriteSheetSlicer sheet = new SpriteSheetSlicer(asset, cfg.sprite.width, cfg.sprite.height, cfg.sprite.frames, cfg.sprite.directions);
        AnimationClip fly = new AnimationClip(sheet, cfg.sprite.frameDuration, 0, 0, cfg.sprite.frames - 1, 0);
        AnimationDirector director = new AnimationDirector();
        director.add("fly", fly);
        attack.addComponent(director);
        attack.addComponent(new AnimationUpdater());
        RotatedSpriteRenderer lightning_rsr = new RotatedSpriteRenderer(cfg.sprite.width, cfg.sprite.height);
        lightning_rsr.setSortLayer(3);
        attack.addComponent(lightning_rsr);

        //Despawner
        attack.addComponent(new AttackOutsideMapDespawner());
        attack.addComponent(new AttackDurationDespawner(cfg.stats.duration));

        //Movement
        attack.addComponent(new Projectile(dir));
        attack.addComponent(new EntityMover(cfg.stats.moveSpeed));

        //Hitbox
        EntityConfig.Hitbox hi = cfg.hitbox;
        attack.addComponent(new Hitbox(hi.width, hi.height, hi.offsetX, hi.offsetY));

        //Damage
        List<HitEffect> effects = new HitEffectFactory(assets).build(cfg.onHitEffects);
        attack.addComponent(new DamageOnHit(owner, cfg.stats.damage, cfg.id, effects));

        return attack;
    }
}

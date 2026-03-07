package com.Betulis.Game2D.game.components.combat;

import java.util.List;

import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.game.components.AABB.Hitbox;
import com.Betulis.Game2D.game.components.AABB.Hurtbox;
import com.Betulis.Game2D.game.components.ai.SlimeAI;
import com.Betulis.Game2D.game.components.stats.Health;
import com.Betulis.Game2D.game.prefabs.attacks.FireballExplosion;
import com.badlogic.gdx.utils.SnapshotArray;


public class DamageOnHit extends Component {
    private final GameObject owner;
    private final float dmg;

    public DamageOnHit(GameObject owner, float dmg) {
        this.owner = owner;
        this.dmg = dmg;
    }
    
    @Override
    public void update(float dt) {
        SnapshotArray<GameObject> gos = getScene().getObjects();
        AABB hitbox = getGameObject().getComponent(Hitbox.class).getWorldBounds();

        for (GameObject target : gos) {
            if (target == owner) continue;
            if (target.getComponent(Hurtbox.class) == null) continue;
            AABB hurtbox = target.getComponent(Hurtbox.class).getWorldBounds();
            if (hitbox.intersects(hurtbox)) {
                target.getComponent(Health.class).applyDamage(dmg);
                onHit(target);
            }
        }
    }

    public void onHit(GameObject target) {
        GameObject go = getGameObject();
        owner.getComponent(CombatState.class).enterCombat();
        target.getComponent(CombatState.class).enterCombat();


        //Aggro
        SlimeAI slimeAI = target.getComponent(SlimeAI.class);
        if (slimeAI != null) {
            slimeAI.aggro(owner.getTransform());
        }

        //Animation
        spawnExplosion(target);

        //Destroy
        go.destroy();
    }

    private void spawnExplosion(GameObject target) {

        GameObject explosion = FireballExplosion.create(target, getScene().getGame().getAssets().getTexture("abilities/fireball/fireball_explode.png"));

        explosion.getTransform().setWorldPosition(
            target.getTransform().getWorldX(),
            target.getTransform().getWorldY()
        );
        getScene().addObject(explosion);
    }
}

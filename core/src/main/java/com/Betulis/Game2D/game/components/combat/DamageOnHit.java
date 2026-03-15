package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.game.components.AABB.Hitbox;
import com.Betulis.Game2D.game.components.AABB.Hurtbox;
import com.Betulis.Game2D.game.components.stats.Health;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.List;

public class DamageOnHit extends Component {
    private final GameObject owner;
    private final float dmg;
    private final String attackId;
    private final List<HitEffect> effects;

    public DamageOnHit(GameObject owner, float dmg, String attackId, List<HitEffect> effects) {
        this.owner = owner;
        this.dmg = dmg;
        this.attackId = attackId;
        this.effects = effects;
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

    private void onHit(GameObject target) {
        owner.getComponent(CombatState.class).enterCombat();
        target.getComponent(CombatState.class).enterCombat();

        HitContext ctx = new HitContext(owner, target, dmg, attackId);

        for (HitEffect effect : effects) {
            effect.apply(ctx, getScene());
        }

        ReactOnHit react = target.getComponent(ReactOnHit.class);
        if (react != null) react.trigger(ctx);

        getGameObject().destroy();
    }
}

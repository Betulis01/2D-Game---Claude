package com.Betulis.Game2D.game.components.movement;

import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.components.stats.Health;

public class ChaseMovement extends Movement {

    private final float stopDistance;
    private final float range;
    private final float speed;
    private Transform target;

    public ChaseMovement(float stopDistance, float range, float speed) {
        this.stopDistance = stopDistance;
        this.range        = range;
        this.speed        = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setTarget(Transform target) {
        this.target = target;
    }

    /** True when the AI should abandon the chase and return to wander. */
    public boolean shouldGiveUp() {
        if (target == null) return true;
        Health h = target.getGameObject().getComponent(Health.class);
        if (h != null && h.isDead()) return true;
        return distToTarget() > range;
    }

    @Override
    public void updateMovement(float dt) {
        if (target == null) {
            moving = false;
            return;
        }

        float dist = distToTarget();

        if (dist <= stopDistance) {
            moving = false;
            return;
        }

        float myX = gameObject.getTransform().getWorldX();
        float myY = gameObject.getTransform().getWorldY();
        float dx  = target.getWorldX() - myX;
        float dy  = target.getWorldY() - myY;

        direction.set(dx / dist, dy / dist);
        moving = true;
    }

    private float distToTarget() {
        float myX = gameObject.getTransform().getWorldX();
        float myY = gameObject.getTransform().getWorldY();
        float dx  = target.getWorldX() - myX;
        float dy  = target.getWorldY() - myY;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}

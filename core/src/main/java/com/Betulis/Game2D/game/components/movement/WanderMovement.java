package com.Betulis.Game2D.game.components.movement;

/**
 * Craig Reynolds' Wander steering behaviour with idle pauses and a home radius.
 *
 * Alternates between two phases:
 *   MOVING — Reynolds' circle-ahead wander produces smooth, arcing paths.
 *   IDLE   — entity stops for a random 2-10 seconds, then picks a fresh
 *             wander angle before resuming.
 *
 * If the entity drifts beyond HOME_RADIUS from its spawn position it
 * immediately steers straight back home, bypassing normal wander until it
 * re-enters the radius.
 */
public class WanderMovement extends Movement {

    private static final float WANDER_DISTANCE = 80f;
    private static final float WANDER_RADIUS   = 40f;
    private static final float WANDER_JITTER   = 2.0f;

    private static final float MOVE_MIN    = 3f;
    private static final float MOVE_MAX    = 7f;
    private static final float IDLE_MIN    = 2f;
    private static final float IDLE_MAX    = 10f;
    private static final float HOME_RADIUS = 100f;

    private enum Phase { MOVING, IDLE }

    private final float speed;
    private float wanderAngle;
    private Phase phase = Phase.MOVING;
    private float phaseTimer;
    private float homeX, homeY;

    public WanderMovement(float speed) {
        this.speed      = speed;
        this.wanderAngle = (float) (Math.random() * Math.PI * 2);
        this.phaseTimer  = randomBetween(MOVE_MIN, MOVE_MAX);
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void start() {
        homeX = gameObject.getTransform().getWorldX();
        homeY = gameObject.getTransform().getWorldY();
    }

    @Override
    public void updateMovement(float dt) {
        if (isBeyondHome()) {
            steerTowardHome();
            return;
        }

        phaseTimer -= dt;

        if (phase == Phase.MOVING) {
            if (phaseTimer <= 0) {
                phase      = Phase.IDLE;
                phaseTimer = randomBetween(IDLE_MIN, IDLE_MAX);
                moving     = false;
                return;
            }
            steer(dt);
        } else {
            if (phaseTimer <= 0) {
                wanderAngle = (float) (Math.random() * Math.PI * 2);
                phase       = Phase.MOVING;
                phaseTimer  = randomBetween(MOVE_MIN, MOVE_MAX);
                steer(dt);
            }
        }
    }

    private boolean isBeyondHome() {
        float dx = gameObject.getTransform().getWorldX() - homeX;
        float dy = gameObject.getTransform().getWorldY() - homeY;
        return dx * dx + dy * dy > HOME_RADIUS * HOME_RADIUS;
    }

    private void steerTowardHome() {
        float dx = homeX - gameObject.getTransform().getWorldX();
        float dy = homeY - gameObject.getTransform().getWorldY();
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        direction.set(dx / len, dy / len);
        wanderAngle = (float) Math.atan2(dy, dx); // sync so re-entry feels continuous
        moving = true;
    }

    private void steer(float dt) {
        wanderAngle += (float) (Math.random() * 2 - 1) * WANDER_JITTER * dt;

        float circleX = direction.x * WANDER_DISTANCE;
        float circleY = direction.y * WANDER_DISTANCE;

        float wanderX = circleX + (float) Math.cos(wanderAngle) * WANDER_RADIUS;
        float wanderY = circleY + (float) Math.sin(wanderAngle) * WANDER_RADIUS;

        float len = (float) Math.sqrt(wanderX * wanderX + wanderY * wanderY);
        if (len > 0) {
            direction.set(wanderX / len, wanderY / len);
        }

        moving = true;
    }

    private static float randomBetween(float min, float max) {
        return min + (float) Math.random() * (max - min);
    }
}

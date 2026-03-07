package com.Betulis.Game2D.game.components.movement;

public class WanderMovement extends Movement {

    public static final float DEFAULT_SPEED    = 40f;
    private static final float WANDER_INTERVAL = 2.0f;

    private float timer = 0f;

    @Override
    public void updateMovement(float dt) {
        timer -= dt;
        if (timer <= 0) {
            float randX = (float) Math.random() * 2 - 1;
            float randY = (float) Math.random() * 2 - 1;
            direction.set(randX, randY);
            direction.normalize();
            moving = true;
            timer = WANDER_INTERVAL;
        }
    }
}

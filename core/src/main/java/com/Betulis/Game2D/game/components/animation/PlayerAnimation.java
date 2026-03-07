package com.Betulis.Game2D.game.components.animation;

import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.game.components.movement.EntityMover;

public class PlayerAnimation extends Component {

    private EntityMover entityMover;
    private AnimationDirector director;

    private Vector2 lastDir;

    @Override
    public void start() {
        entityMover = getGameObject().getComponent(EntityMover.class);
        director    = getGameObject().getComponent(AnimationDirector.class);
        lastDir     = new Vector2(0, 1);
    }

    @Override
    public void update(float dt) {
        if (entityMover == null || director == null) return;

        if (entityMover.isMoving()) {
            Vector2 dir = entityMover.getDirection();
            walkDirectionFromVector(dir);
        } else {
            idleDirectionFromVector(lastDir);
        }
    }

    private void walkDirectionFromVector(Vector2 dir) {
        float x = dir.x;
        float y = dir.y;

        if (x > 0 && y > 0) director.play("walk_up_right", true);
        else if (x > 0 && y < 0) director.play("walk_down_right", true);
        else if (x < 0 && y > 0) director.play("walk_up_left", true);
        else if (x < 0 && y < 0) director.play("walk_down_left", true);
        else if (x > 0) director.play("walk_right", true);
        else if (x < 0) director.play("walk_left", true);
        else if (y > 0) director.play("walk_up", true);
        else if (y < 0) director.play("walk_down", true);

        lastDir.set(x, y);
    }

    private void idleDirectionFromVector(Vector2 lastDir) {
        float x = lastDir.x;
        float y = lastDir.y;

        if (x > 0 && y > 0) director.play("idle_up_right", false);
        else if (x > 0 && y < 0) director.play("idle_down_right", false);
        else if (x < 0 && y > 0) director.play("idle_up_left", false);
        else if (x < 0 && y < 0) director.play("idle_down_left", false);
        else if (x > 0) director.play("idle_right", false);
        else if (x < 0) director.play("idle_left", false);
        else if (y > 0) director.play("idle_up", false);
        else if (y < 0) director.play("idle_down", false);
    }
}

package com.Betulis.Game2D.game.components.animation;

import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.game.components.movement.EntityMover;

public class SlimeAnimation extends Component {

    private EntityMover entityMover;
    private AnimationDirector director;

    @Override
    public void start() {
        entityMover = getGameObject().getComponent(EntityMover.class);
        director    = getGameObject().getComponent(AnimationDirector.class);
    }

    @Override
    public void update(float dt) {
        if (entityMover == null || director == null) return;
        if (!director.isLooping() && !director.isFinished()) return;

        if (entityMover.isMoving()) {
            director.play("jump");
        } else {
            director.play("idle");
        }
    }
}

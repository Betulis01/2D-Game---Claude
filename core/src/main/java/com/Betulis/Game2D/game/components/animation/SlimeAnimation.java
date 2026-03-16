package com.Betulis.Game2D.game.components.animation;

import com.Betulis.Game2D.engine.render.SimpleAnimRenderer;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.game.components.movement.EntityMover;

public class SlimeAnimation extends Component {

    private EntityMover        entityMover;
    private SimpleAnimRenderer renderer;

    @Override
    public void start() {
        entityMover = getGameObject().getComponent(EntityMover.class);
        renderer    = getGameObject().getComponent(SimpleAnimRenderer.class);
    }

    @Override
    public void update(float dt) {
        if (entityMover == null || renderer == null) return;
        // Don't interrupt a non-looping animation (e.g. attack) while it's playing
        if (!renderer.isLooping() && !renderer.isFinished()) return;

        if (entityMover.isMoving()) {
            renderer.play("jump");
        } else {
            renderer.play("idle");
        }
    }
}

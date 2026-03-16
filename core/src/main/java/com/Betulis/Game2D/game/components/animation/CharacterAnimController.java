package com.Betulis.Game2D.game.components.animation;

import com.Betulis.Game2D.engine.animation.CharacterDirection;
import com.Betulis.Game2D.engine.animation.CharacterState;
import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.render.LayeredSpriteRenderer;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.game.components.movement.EntityMover;

/**
 * Reads EntityMover movement and drives LayeredSpriteRenderer state/direction.
 * Replaces PlayerAnimation.
 */
public class CharacterAnimController extends Component {

    private EntityMover          entityMover;
    private LayeredSpriteRenderer renderer;
    private CharacterDirection    lastDir = CharacterDirection.S;

    @Override
    public void start() {
        entityMover = getGameObject().getComponent(EntityMover.class);
        renderer    = getGameObject().getComponent(LayeredSpriteRenderer.class);
    }

    @Override
    public void update(float dt) {
        if (entityMover == null || renderer == null) return;

        if (entityMover.isMoving()) {
            Vector2 dir = entityMover.getDirection();
            lastDir = CharacterDirection.fromVector(dir.x, dir.y);
            renderer.setStateDir(CharacterState.WALK, lastDir);
        } else {
            renderer.setStateDir(CharacterState.IDLE, lastDir);
        }
    }
}

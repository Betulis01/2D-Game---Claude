package com.Betulis.Game2D.game.components.ai;

import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.components.movement.ChaseMovement;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.WanderMovement;

public class SlimeAI extends Component {

    private WanderMovement wander;
    private ChaseMovement  chase;
    private EntityMover    entityMover;

    @Override
    public void start() {
        wander      = gameObject.getComponent(WanderMovement.class);
        chase       = gameObject.getComponent(ChaseMovement.class);
        entityMover = gameObject.getComponent(EntityMover.class);
        enterWander();
    }

    @Override
    public void update(float dt) {
        if (entityMover.getMovement() == chase && chase.shouldGiveUp()) {
            enterWander();
        }
    }

    /** Called by DamageOnHit when this slime is struck. */
    public void aggro(Transform attacker) {
        chase.setTarget(attacker);
        entityMover.setMovement(chase);
        entityMover.setSpeed(ChaseMovement.DEFAULT_SPEED);
    }

    private void enterWander() {
        chase.setTarget(null);
        entityMover.setMovement(wander);
        entityMover.setSpeed(WanderMovement.DEFAULT_SPEED);
    }
}

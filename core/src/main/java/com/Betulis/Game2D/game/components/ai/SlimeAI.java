package com.Betulis.Game2D.game.components.ai;

import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.components.combat.AttackSpawner;
import com.Betulis.Game2D.game.components.movement.ChaseMovement;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.WanderMovement;

public class SlimeAI extends Component {

    private static final float ATTACK_RANGE = 100f;
    private static final float COOLDOWN_MIN = 3f;
    private static final float COOLDOWN_MAX = 5f;

    private WanderMovement    wander;
    private ChaseMovement     chase;
    private EntityMover       entityMover;
    private AnimationDirector director;
    private AttackSpawner     attackSpawner;
    private Transform         attackTarget;
    private float             attackTimer;
    private boolean           isChasing;

    public SlimeAI(AttackSpawner attackSpawner) {
        this.attackSpawner = attackSpawner;
    }

    @Override
    public void start() {
        wander      = gameObject.getComponent(WanderMovement.class);
        chase       = gameObject.getComponent(ChaseMovement.class);
        entityMover = gameObject.getComponent(EntityMover.class);
        director    = gameObject.getComponent(AnimationDirector.class);
        enterWander();
    }

    @Override
    public void update(float dt) {
        if (!isChasing) return;


        if (chase.shouldGiveUp()) {
            enterWander();
            return;
        }

        attackTimer -= dt;
        float myX = gameObject.getTransform().getWorldX();
        float myY = gameObject.getTransform().getWorldY();
        float dx  = attackTarget.getWorldX() - myX;
        float dy  = attackTarget.getWorldY() - myY;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (attackTimer <= 0 && dist <= ATTACK_RANGE) {
            attackSpawner.tryAttack(dx, dy);
            director.play("attack", false);
            attackTimer = COOLDOWN_MIN + (float)(Math.random() * (COOLDOWN_MAX - COOLDOWN_MIN));
        }
    }

    /** Called by ReactOnHit when this slime is struck. */
    public void aggro(Transform attacker) {
        chase.setTarget(attacker);
        entityMover.setMovement(chase);
        entityMover.setSpeed(chase.getSpeed());
        attackTarget = attacker;
        isChasing    = true;
        attackTimer  = COOLDOWN_MIN + (float)(Math.random() * (COOLDOWN_MAX - COOLDOWN_MIN));
    }

    private void enterWander() {
        chase.setTarget(null);
        entityMover.setMovement(wander);
        entityMover.setSpeed(wander.getSpeed());
        attackTarget = null;
        isChasing    = false;
    }
}

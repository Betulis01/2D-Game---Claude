package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.render.SimpleAnimRenderer;
import com.Betulis.Game2D.engine.system.Component;

public class AttackDurationDespawner extends Component {
    private double duration;
    private double despawnTimer;
    private SimpleAnimRenderer renderer;

    public AttackDurationDespawner(double duration) {
        this.duration = duration;
    }

    @Override
    public void start() {
        renderer = getGameObject().getComponent(SimpleAnimRenderer.class);
    }

    @Override
    public void update(float dt) {
        duration -= dt;
        if (duration <= 0) {
            despawnAnimation(dt);
        }
    }

    private void despawnAnimation(double dt) {
        despawnTimer += dt;
        if (despawnTimer > 0.5) {
            getGameObject().destroy();
            despawnTimer = 0;
        } else if (renderer != null) {
            renderer.play("noHit", false);
        }
    }
}

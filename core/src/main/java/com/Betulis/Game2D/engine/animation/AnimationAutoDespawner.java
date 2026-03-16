package com.Betulis.Game2D.engine.animation;

import com.Betulis.Game2D.engine.render.SimpleAnimRenderer;
import com.Betulis.Game2D.engine.system.Component;

/** Destroys its GameObject when the current non-looping animation finishes. */
public class AnimationAutoDespawner extends Component {

    private SimpleAnimRenderer renderer;

    @Override
    public void start() {
        renderer = getGameObject().getComponent(SimpleAnimRenderer.class);
    }

    @Override
    public void update(float dt) {
        if (renderer != null && renderer.isFinished()) {
            getGameObject().destroy();
        }
    }
}

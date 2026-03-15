package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;

import java.util.function.Function;

public class SpawnHitEffect implements HitEffect {
    private final Function<GameObject, GameObject> factory;

    public SpawnHitEffect(Function<GameObject, GameObject> factory) {
        this.factory = factory;
    }

    @Override
    public void apply(HitContext ctx, Scene scene) {
        scene.addObject(factory.apply(ctx.target));
    }
}

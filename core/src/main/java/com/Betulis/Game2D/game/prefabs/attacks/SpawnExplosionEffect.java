package com.Betulis.Game2D.game.prefabs.attacks;

import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;
import com.Betulis.Game2D.game.components.combat.HitContext;
import com.Betulis.Game2D.game.components.combat.HitEffect;

import java.util.function.Function;

public class SpawnExplosionEffect implements HitEffect {
    private final Function<GameObject, GameObject> factory;

    public SpawnExplosionEffect(Function<GameObject, GameObject> factory) {
        this.factory = factory;
    }

    @Override
    public void apply(HitContext ctx, Scene scene) {
        scene.addObject(factory.apply(ctx.target));
    }
}

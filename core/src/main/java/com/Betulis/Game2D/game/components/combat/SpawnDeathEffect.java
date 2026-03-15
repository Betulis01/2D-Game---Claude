package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;

import java.util.function.BiFunction;

public class SpawnDeathEffect implements DeathEffect {
    private final BiFunction<Float, Float, GameObject> factory;

    public SpawnDeathEffect(BiFunction<Float, Float, GameObject> factory) {
        this.factory = factory;
    }

    @Override
    public void apply(GameObject source, Scene scene) {
        float x = source.getTransform().getWorldX();
        float y = source.getTransform().getWorldY();
        scene.addObject(factory.apply(x, y));
    }
}

package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.system.Scene;
import com.Betulis.Game2D.game.prefabs.FloatingTextPrefab;
import com.badlogic.gdx.graphics.Color;

public class FloatingDamageEffect implements HitEffect {
    @Override
    public void apply(HitContext ctx, Scene scene) {
        float tx = ctx.target.getTransform().getWorldX();
        float ty = ctx.target.getTransform().getWorldY() + 16f;
        scene.addObject(FloatingTextPrefab.create(tx, ty, "" + (int) ctx.damage, Color.RED, 1.5f));
    }
}

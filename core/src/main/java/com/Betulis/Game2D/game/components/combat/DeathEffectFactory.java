package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.prefabs.mobs.slime.SlimeDeathEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeathEffectFactory {
    private final Map<String, DeathEffect> registry = new HashMap<>();

    public DeathEffectFactory(Assets assets) {
        registry.put("slime_death_animation", new SpawnDeathEffect(
            (x, y) -> SlimeDeathEffect.create(x, y, assets)
        ));
    }

    public List<DeathEffect> build(String[] ids) {
        List<DeathEffect> effects = new ArrayList<>();
        if (ids == null) return effects;
        for (String id : ids) {
            DeathEffect effect = registry.get(id);
            if (effect != null) effects.add(effect);
        }
        return effects;
    }
}

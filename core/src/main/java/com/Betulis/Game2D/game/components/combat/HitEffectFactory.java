package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.prefabs.attacks.FireballExplosion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HitEffectFactory {
    private final Map<String, HitEffect> registry = new HashMap<>();

    public HitEffectFactory(Assets assets) {
        registry.put("fireball_explosion", new SpawnExplosionEffect(
            target -> FireballExplosion.create(target, assets.getTexture(Assets.fireball_explode))
        ));
        registry.put("floating_damage", new FloatingDamageEffect());
    }

    public List<HitEffect> build(String[] ids) {
        List<HitEffect> effects = new ArrayList<>();
        if (ids == null) return effects;
        for (String id : ids) {
            HitEffect effect = registry.get(id);
            if (effect != null) effects.add(effect);
        }
        return effects;
    }
}

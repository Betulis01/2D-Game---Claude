package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.system.Component;

import java.util.List;

public class DeathEffects extends Component {
    private final List<DeathEffect> effects;

    public DeathEffects(List<DeathEffect> effects) {
        this.effects = effects;
    }

    public void add(DeathEffect effect) {
        effects.add(effect);
    }

    public void trigger() {
        effects.forEach(e -> e.apply(getGameObject(), getScene()));
    }
}

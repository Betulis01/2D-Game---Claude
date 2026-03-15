package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.system.GameObject;

public class HitContext {
    public final GameObject attacker;
    public final GameObject target;
    public final float damage;
    public final String attackId;

    public HitContext(GameObject attacker, GameObject target, float damage, String attackId) {
        this.attacker = attacker;
        this.target = target;
        this.damage = damage;
        this.attackId = attackId;
    }
}

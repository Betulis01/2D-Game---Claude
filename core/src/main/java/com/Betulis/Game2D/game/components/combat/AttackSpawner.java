package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.game.prefabs.attacks.AttackPrefabs;

public final class AttackSpawner extends Component {
    private EntityConfig attack;
    private float cooldownTimer;

    public void setAttack(EntityConfig attack) {
        this.attack = attack;
    }

    @Override
    public void update(float dt) {
        if (cooldownTimer > 0) {
            cooldownTimer -= dt;
        }
    }

    public void tryAttack(float dirX, float dirY) {
        if (attack == null || cooldownTimer > 0) return;

        Vector2 dir = new Vector2(dirX, dirY).normalize();
        spawnAttack(attack, dir);

        cooldownTimer = attack.stats.cooldown;
    }

    private void spawnAttack(EntityConfig attack, Vector2 dir) {
        var assets = getScene().getGame().getAssets();
        GameObject obj = switch (attack.id) {
            case "lightning_bolt" -> AttackPrefabs.createLightningBolt(attack, getGameObject(), dir, assets);
            case "fireball"       -> AttackPrefabs.createFireball(attack, getGameObject(), dir, assets);
            case "slime_spit"     -> AttackPrefabs.createSlimeSpit(attack, getGameObject(), dir, assets);
            default -> throw new IllegalArgumentException();
        };
        getScene().addObject(obj);
    }
}


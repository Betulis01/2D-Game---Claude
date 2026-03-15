package com.Betulis.Game2D.game.components.stats;

import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.game.components.combat.DeathEffects;
import com.Betulis.Game2D.game.prefabs.FloatingTextPrefab;
import com.badlogic.gdx.graphics.Color;

public class XPReward extends Component {
    private static final Color XP_COLOR = new Color(0.4f, 0f, 0.55f, 1f);

    private final float amount;

    public XPReward(float amount) { this.amount = amount; }

    @Override
    public void start() {
        DeathEffects deathEffects = getGameObject().getComponent(DeathEffects.class);
        if (deathEffects == null) return;

        // Find PlayerXP once at start — avoids iterating scene objects during death (nested iterator)
        PlayerXP playerXP = findPlayerXP();

        deathEffects.add((source, scene) -> {
            if (playerXP != null) playerXP.addXP(amount);
            float dx = source.getTransform().getWorldX();
            float dy = source.getTransform().getWorldY();
            scene.addOverlayObject(FloatingTextPrefab.create(dx, dy + 10f, "+" + (int) amount + " XP", XP_COLOR, 1.2f));
        });
    }

    private PlayerXP findPlayerXP() {
        for (GameObject obj : getScene().getObjects()) {
            PlayerXP xp = obj.getComponent(PlayerXP.class);
            if (xp != null) return xp;
        }
        return null;
    }

    public float getAmount() { return amount; }
}

package com.Betulis.Game2D.game.components.stats;

import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.game.prefabs.FloatingTextPrefab;
import com.badlogic.gdx.graphics.Color;

public class XPReward extends Component {
    private static final Color XP_COLOR = new Color(0.4f, 0f, 0.55f, 1f);

    private final float amount;

    public XPReward(float amount) { this.amount = amount; }

    @Override
    public void start() {
        Health health = getGameObject().getComponent(Health.class);
        if (health == null) return;

        // Find PlayerXP once at start — avoids iterating scene objects during death (nested iterator)
        PlayerXP playerXP = findPlayerXP();

        health.setDeathListener((dx, dy, scene) -> {
            if (playerXP != null) playerXP.addXP(amount);
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

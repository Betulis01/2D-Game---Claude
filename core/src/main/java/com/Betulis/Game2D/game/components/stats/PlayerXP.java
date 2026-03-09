package com.Betulis.Game2D.game.components.stats;

import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.system.Component;

public class PlayerXP extends Component {
    private float currentXP;
    private float maxXP;

    public void init(EntityConfig.Stats stats) {
        this.currentXP = stats.xp;
        this.maxXP = stats.maxXp;
    }

    public float getCurrentXP() { return currentXP; }
    public float getMaxXP() { return maxXP; }
    public void addXP(float amount) { currentXP = Math.min(currentXP + amount, maxXP); }
}

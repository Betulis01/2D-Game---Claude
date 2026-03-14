package com.Betulis.Game2D.game.components.stats;

import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.system.Component;

public class PlayerXP extends Component {
    private float currentXP;
    private float maxXP;
    private int   level = 1;

    private float gainStartRatio;
    private float gainEndRatio;
    private float gainTimer = 0f;
    private static final float GAIN_DISPLAY = 0.8f;

    private Runnable onLevelUp;

    public void init(EntityConfig.Stats stats) {
        this.currentXP = stats.xp;
        this.maxXP     = stats.maxXp;
    }

    @Override
    public void update(float dt) {
        if (gainTimer > 0) gainTimer = Math.max(0, gainTimer - dt);
    }

    public boolean addXP(float amount) {
        float startRatio = currentXP / maxXP;
        currentXP += amount;
        boolean leveled = false;
        while (currentXP >= maxXP) {
            currentXP -= maxXP;
            level++;
            leveled = true;
        }
        if (!leveled) {
            gainStartRatio = startRatio;
            gainEndRatio   = currentXP / maxXP;
            gainTimer      = GAIN_DISPLAY;
        }
        if (leveled && onLevelUp != null) onLevelUp.run();
        return leveled;
    }

    public float getCurrentXP()     { return currentXP; }
    public float getMaxXP()         { return maxXP; }
    public int   getLevel()         { return level; }
    public float getGainTimer()     { return gainTimer; }
    public float getGainStartRatio(){ return gainStartRatio; }
    public float getGainEndRatio()  { return gainEndRatio; }

    public void setOnLevelUp(Runnable r) { this.onLevelUp = r; }
}

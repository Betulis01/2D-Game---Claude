package com.Betulis.Game2D.game.components.stats;

import com.Betulis.Game2D.engine.system.Component;

public class XPReward extends Component {
    private final float amount;

    public XPReward(float amount) { this.amount = amount; }
    public float getAmount()      { return amount; }
}

package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.system.Scene;

public interface HitEffect {
    void apply(HitContext ctx, Scene scene);
}

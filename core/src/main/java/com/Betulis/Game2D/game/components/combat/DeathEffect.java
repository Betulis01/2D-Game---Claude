package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;

public interface DeathEffect {
    void apply(GameObject source, Scene scene);
}

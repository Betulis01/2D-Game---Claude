package com.Betulis.Game2D.game.prefabs;

import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.game.components.FloatingText;
import com.badlogic.gdx.graphics.Color;

public class FloatingTextPrefab {
    public static GameObject create(float x, float y, String text, Color color, float duration) {
        GameObject obj = new GameObject("floating_text");
        obj.getTransform().setPosition(x, y);
        obj.addComponent(new FloatingText(text, color, duration));
        return obj;
    }
}

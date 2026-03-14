package com.Betulis.Game2D.game.prefabs.items;

import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.game.components.items.WorldItem;
import com.Betulis.Game2D.game.inventory.ItemDefinition;

public class WorldItemPrefab {

    public static GameObject create(float x, float y, ItemDefinition itemDef) {
        GameObject obj = new GameObject("WorldItem");
        obj.getComponent(com.Betulis.Game2D.engine.system.Transform.class).setPosition(x, y);
        obj.addComponent(new WorldItem(itemDef));
        return obj;
    }
}

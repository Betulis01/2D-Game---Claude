package com.Betulis.Game2D.game.components.items;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.game.inventory.ItemDefinition;
import com.Betulis.Game2D.game.ui.UIManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldItem extends Component {

    private final ItemDefinition itemDef;

    public WorldItem(ItemDefinition itemDef) {
        this.itemDef = itemDef;
    }

    @Override
    public void render(SpriteBatch batch) {
        Texture icon = itemDef.getIcon();
        if (icon == null) return;

        Camera cam = getScene().getCamera();
        float zoom = cam.getZoom();
        float sx = cam.worldToScreenX(transform.getWorldX());
        float sy = cam.worldToScreenY(transform.getWorldY());
        float size = 16f * zoom;

        batch.setColor(Color.WHITE);
        batch.draw(icon, sx - size * 0.5f, sy - size * 0.5f, size, size);
    }

    public void tryPickup() {
        UIManager ui = getScene().getGame().getUI();
        if (ui.getInventory().addItem(itemDef)) {
            ui.refreshBag();
            gameObject.destroy();
        }
    }
}

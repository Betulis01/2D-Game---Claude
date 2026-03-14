package com.Betulis.Game2D.game.components.items;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.components.movement.PlayerController;
import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.inventory.ItemDefinition;
import com.Betulis.Game2D.game.ui.UIManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldItem extends Component {

    private static final float PICKUP_RANGE = 60f;

    private final ItemDefinition itemDef;
    private Transform playerTransform;
    private Texture promptTexture;
    private boolean showPrompt;
    private InputBindings input;

    public WorldItem(ItemDefinition itemDef) {
        this.itemDef = itemDef;
    }

    @Override
    public void start() {
        for (GameObject obj : getScene().getObjects()) {
            if (obj.getComponent(PlayerController.class) != null) {
                playerTransform = obj.getTransform();
                break;
            }
        }
        if (Gdx.files.local("ui/pickup_prompt.png").exists()) {
            promptTexture = new Texture(Gdx.files.local("ui/pickup_prompt.png"));
        }
        input = getScene().getGame().getInput();
    }

    @Override
    public void update(float dt) {
        if (playerTransform == null) return;
        float dx = playerTransform.getWorldX() - transform.getWorldX();
        float dy = playerTransform.getWorldY() - transform.getWorldY();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        showPrompt = dist < PICKUP_RANGE;
        if (showPrompt && input.isPressed(InputBindings.Action.PICKUP_ITEM)) {
            tryPickup();
        }
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

        if (showPrompt && promptTexture != null) {
            float pSize = 10f * zoom;
            batch.draw(promptTexture, sx - pSize * 0.5f, sy + size * 0.5f + 4f, pSize, pSize);
        }
    }

    private void tryPickup() {
        UIManager ui = getScene().getGame().getUI();
        if (ui.getInventory().addItem(itemDef)) {
            ui.refreshBag();
            gameObject.destroy();
        }
    }

    @Override
    public void onDestroy() {
        if (promptTexture != null) {
            promptTexture.dispose();
            promptTexture = null;
        }
    }
}

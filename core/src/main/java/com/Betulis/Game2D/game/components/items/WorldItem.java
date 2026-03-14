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

    private static final float PICKUP_RANGE = 20f;

    private final ItemDefinition itemDef;
    private Transform playerTransform;
    private Texture interactTexture;
    private boolean showInteract;
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
        if (Gdx.files.local("assets/ui/interact_button.png").exists()) {
            interactTexture = new Texture(Gdx.files.local("ui/interact_button.png"));
        }
        input = getScene().getGame().getInput();
    }

    @Override
    public void update(float dt) {
        if (playerTransform == null) return;
        float dx = playerTransform.getWorldX() - transform.getWorldX();
        float dy = playerTransform.getWorldY() - transform.getWorldY();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        showInteract = dist < PICKUP_RANGE;
        if (showInteract && input.isPressed(InputBindings.Action.PICKUP_ITEM)) {
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

        if (showInteract && interactTexture != null) {
            float pSize = 16f * zoom;
            batch.draw(interactTexture, sx - pSize * 0.5f, sy + size * 0.5f, pSize, pSize);
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
        if (interactTexture != null) {
            interactTexture.dispose();
            interactTexture = null;
        }
    }
}

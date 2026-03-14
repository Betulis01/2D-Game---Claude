package com.Betulis.Game2D.game.components;

import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.components.movement.PlayerController;
import com.Betulis.Game2D.game.input.InputBindings;

public class Interactable extends Component {

    private final float range;
    private final InputBindings.Action action;
    private final Runnable onInteract;

    private Transform playerTransform;
    private boolean inRange;
    private GameObject promptObject;

    public Interactable(float range, InputBindings.Action action, Runnable onInteract) {
        this.range = range;
        this.action = action;
        this.onInteract = onInteract;
    }

    @Override
    public void start() {
        for (GameObject obj : getScene().getObjects()) {
            if (obj.getComponent(PlayerController.class) != null) {
                playerTransform = obj.getTransform();
                break;
            }
        }
        promptObject = new GameObject("InteractPrompt");
        promptObject.addComponent(new InteractButton(this));
        getScene().addOverlayObject(promptObject);
    }

    @Override
    public void update(float dt) {
        if (playerTransform == null) return;
        float dx = playerTransform.getWorldX() - transform.getWorldX();
        float dy = playerTransform.getWorldY() - transform.getWorldY();
        inRange = (dx * dx + dy * dy) < range * range;
        if (inRange && getScene().getGame().getInput().isPressed(action)) {
            onInteract.run();
        }
    }

    @Override
    public void onDestroy() {
        if (promptObject != null) {
            getScene().removeOverlayObject(promptObject);
            promptObject.onDestroy();
            promptObject = null;
        }
    }

    public boolean isInRange() {
        return inRange;
    }
}

package com.Betulis.Game2D.game.input;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public final class InputBindings extends InputAdapter {

    private final Map<Action, Integer> keyBindings;
    private final Map<Action, Integer> mouseBindings;
    private float scrollDelta;

    public enum Action {
        DEBUG,
        MOVE_LEFT,
        MOVE_RIGHT,
        MOVE_UP,
        MOVE_DOWN,
        SPELL_1, SPELL_2, SPELL_3, SPELL_4,
        MOUSE_SPELL_1, MOUSE_SPELL_2,
        TOGGLE_BAG, TOGGLE_CHARACTER, TOGGLE_SPELLBOOK, TOGGLE_TALENT,
        PICKUP_ITEM
    }

    public InputBindings() {
        this.keyBindings = new EnumMap<>(Action.class);
        this.mouseBindings = new EnumMap<>(Action.class);

        // Movement
        keyBindings.put(Action.DEBUG,       Input.Keys.SPACE);
        keyBindings.put(Action.MOVE_LEFT,   Input.Keys.A);
        keyBindings.put(Action.MOVE_RIGHT,  Input.Keys.D);
        keyBindings.put(Action.MOVE_UP,     Input.Keys.W);
        keyBindings.put(Action.MOVE_DOWN,   Input.Keys.S);

        // Keyboard spell bar
        keyBindings.put(Action.SPELL_1, Input.Keys.Q);
        keyBindings.put(Action.SPELL_2, Input.Keys.E);
        keyBindings.put(Action.SPELL_3, Input.Keys.R);
        keyBindings.put(Action.SPELL_4, Input.Keys.T);

        // Mouse spell bar — LEFT = slot 0, RIGHT = slot 1wd
        mouseBindings.put(Action.MOUSE_SPELL_1, Input.Buttons.LEFT);
        mouseBindings.put(Action.MOUSE_SPELL_2, Input.Buttons.RIGHT);

        // Interaction
        keyBindings.put(Action.PICKUP_ITEM, Input.Keys.F);

        // Panel toggles
        keyBindings.put(Action.TOGGLE_BAG,       Input.Keys.B);
        keyBindings.put(Action.TOGGLE_CHARACTER, Input.Keys.C);
        keyBindings.put(Action.TOGGLE_SPELLBOOK, Input.Keys.N);
        keyBindings.put(Action.TOGGLE_TALENT,    Input.Keys.T);
    }

    /** Returns true once on the frame the action is triggered (just-pressed). */
    public boolean isPressed(Action action) {
        if (keyBindings.containsKey(action)) {
            return Gdx.input.isKeyJustPressed(keyBindings.get(action));
        }
        if (mouseBindings.containsKey(action)) {
            return Gdx.input.isButtonJustPressed(mouseBindings.get(action));
        }
        return false;
    }

    /** Returns true while the action input is held down. */
    public boolean isHeld(Action action) {
        if (keyBindings.containsKey(action)) {
            return Gdx.input.isKeyPressed(keyBindings.get(action));
        }
        if (mouseBindings.containsKey(action)) {
            return Gdx.input.isButtonPressed(mouseBindings.get(action));
        }
        return false;
    }

    /**
     * Returns a short display name for the current binding of the given action,
     * e.g. "1", "LMB", "RMB". Returns null if the action has no binding.
     */
    public String getDisplayName(Action action) {
        if (keyBindings.containsKey(action)) {
            return Input.Keys.toString(keyBindings.get(action));
        }
        if (mouseBindings.containsKey(action)) {
            int btn = mouseBindings.get(action);
            if (btn == Input.Buttons.LEFT)   return "LMB";
            if (btn == Input.Buttons.RIGHT)  return "RMB";
            if (btn == Input.Buttons.MIDDLE) return "MMB";
            return "M" + btn;
        }
        return null;
    }

    public void bind(Action action, int key) {
        keyBindings.put(action, key);
    }

    public int getBinding(Action action) {
        return keyBindings.getOrDefault(action, -1);
    }

    // --- INPUT PROCESSOR OVERRIDES ---

    @Override
    public boolean scrolled(float amountX, float amountY) {
        this.scrollDelta = amountY;
        return true;
    }

    /** Returns the scroll amount and resets it for the next frame. */
    public float getZoomDelta() {
        float delta = -scrollDelta;
        scrollDelta = 0;
        return delta;
    }
}

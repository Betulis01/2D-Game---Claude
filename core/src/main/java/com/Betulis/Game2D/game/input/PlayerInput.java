package com.Betulis.Game2D.game.input;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.components.combat.AttackSpawner;
import com.Betulis.Game2D.game.ui.UIManager;
import com.Betulis.Game2D.game.ui.data.SpellBar;
import com.Betulis.Game2D.game.ui.data.SpellDefinition;
import com.badlogic.gdx.Gdx;

public final class PlayerInput extends Component {

    private InputBindings input;

    @Override
    public void start() {
        input = getScene().getGame().getInput();
    }

    @Override
    public void update(float dt) {
        UIManager ui = getScene().getGame().getUI();

        // Keyboard spell bar (1-4)
        SpellBar keyBar = ui.getSpellBarData();
        InputBindings.Action[] keyActions = {
            InputBindings.Action.SPELL_1, InputBindings.Action.SPELL_2,
            InputBindings.Action.SPELL_3
        };
        for (int i = 0; i < 3; i++) {
            if (input.isPressed(keyActions[i])) {
                SpellDefinition spell = keyBar.getSlot(i);
                if (spell != null) castAbility(spell.getConfigPath());
            }
        }

        // Mouse spell bar (LMB / RMB) — guard against UI hover AND drag
        float uiMx = Gdx.input.getX();
        float uiMy = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (!ui.isDragging() && !ui.isMouseOverUI(uiMx, uiMy)) {
            SpellBar mouseBar = ui.getMouseSpellBarData();
            InputBindings.Action[] mouseActions = {
                InputBindings.Action.MOUSE_SPELL_1,
                InputBindings.Action.MOUSE_SPELL_2
            };
            for (int i = 0; i < 2; i++) {
                if (input.isPressed(mouseActions[i])) {
                    SpellDefinition spell = mouseBar.getSlot(i);
                    if (spell != null) castAbility(spell.getConfigPath());
                }
            }
        }
    }

    private void castAbility(String configPath) {
        Camera cam = getScene().getCamera();

        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();

        float wx = cam.screenToWorldX(screenX);
        float wy = cam.screenToWorldY(screenY);

        Transform t = getGameObject().getTransform();
        float dx = wx - t.getWorldX();
        float dy = wy - t.getWorldY();

        AttackSpawner spawner = getGameObject().getComponent(AttackSpawner.class);
        spawner.setAttack(new ConfigLoader().load(configPath));
        spawner.tryAttack(dx, dy);
    }
}

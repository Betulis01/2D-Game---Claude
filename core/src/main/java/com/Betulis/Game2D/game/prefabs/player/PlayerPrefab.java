package com.Betulis.Game2D.game.prefabs.player;

import com.Betulis.Game2D.engine.animation.CharacterState;
import com.Betulis.Game2D.engine.animation.DirectionalAnimSet;
import com.Betulis.Game2D.engine.config.ConfigLoader;
import com.Betulis.Game2D.engine.config.EntityConfig;
import com.Betulis.Game2D.engine.render.LayeredSpriteRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.components.AABB.Hurtbox;
import com.Betulis.Game2D.game.components.animation.CharacterAnimController;
import com.Betulis.Game2D.game.components.combat.AttackSpawner;
import com.Betulis.Game2D.game.components.combat.CombatState;
import com.Betulis.Game2D.game.components.movement.EntityMover;
import com.Betulis.Game2D.game.components.movement.PlayerController;
import com.Betulis.Game2D.game.components.render.EquipmentLayerManager;
import com.Betulis.Game2D.game.components.stats.Health;
import com.Betulis.Game2D.game.components.stats.PlayerXP;
import com.Betulis.Game2D.game.input.PlayerInput;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

public class PlayerPrefab {

    public GameObject create(float x, float y, Assets assets) {
        EntityConfig cfg = new ConfigLoader().load("data/config/player.json");
        GameObject playerObj = new GameObject("Player");

        // Transform
        playerObj.getComponent(Transform.class).setPosition(x, y);

        // Movement
        playerObj.addComponent(new PlayerController());
        playerObj.addComponent(new EntityMover(cfg.stats.moveSpeed));

        // Renderer
        LayeredSpriteRenderer renderer = new LayeredSpriteRenderer(cfg.sprite.width, cfg.sprite.height);
        playerObj.addComponent(renderer);

        // Body layer from atlas
        TextureAtlas orcAtlas = assets.getAtlas(Assets.orc_atlas);
        Map<CharacterState, DirectionalAnimSet> bodyLayer = new HashMap<>();
        bodyLayer.put(CharacterState.IDLE, new DirectionalAnimSet(orcAtlas, "idle", cfg.sprite.idleFrameDuration));
        bodyLayer.put(CharacterState.WALK, new DirectionalAnimSet(orcAtlas, "walk", cfg.sprite.frameDuration));
        renderer.addLayer(bodyLayer);

        // Animation controller
        playerObj.addComponent(new CharacterAnimController());

        // Equipment visual layer manager
        playerObj.addComponent(new EquipmentLayerManager());

        // Health
        playerObj.addComponent(new Health(cfg.stats.maxHealth, 0));

        // XP
        PlayerXP xp = new PlayerXP();
        xp.init(cfg.stats);
        playerObj.addComponent(xp);

        // Combat
        playerObj.addComponent(new CombatState());

        // Hurtbox
        playerObj.addComponent(new Hurtbox(cfg.hurtbox.width, cfg.hurtbox.height,
                cfg.hurtbox.offsetX, cfg.hurtbox.offsetY));

        // Attack
        playerObj.addComponent(new AttackSpawner());
        playerObj.addComponent(new PlayerInput());

        return playerObj;
    }
}

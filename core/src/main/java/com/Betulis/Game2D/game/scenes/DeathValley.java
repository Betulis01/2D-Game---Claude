package com.Betulis.Game2D.game.scenes;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.render.ObjectLayerRenderer;
import com.Betulis.Game2D.engine.render.TileMapRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.tiled.TiledMapLoader;
import com.Betulis.Game2D.game.components.stats.Health;
import com.Betulis.Game2D.game.components.stats.PlayerXP;
import com.Betulis.Game2D.game.components.stats.XPReward;
import com.Betulis.Game2D.game.prefabs.FloatingTextPrefab;
import com.badlogic.gdx.graphics.Color;
import com.Betulis.Game2D.game.prefabs.camera.CameraPrefab;
import com.Betulis.Game2D.game.prefabs.mobs.SlimePrefab;
import com.Betulis.Game2D.game.prefabs.player.PlayerPrefab;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathValley extends Scene {
    private GameObject mapObject;
    private PlayerXP playerXP;

    public DeathValley() {
        super(); 
    }

    @Override
    public void onLoad() {
        //Map
        TiledMapLoader loader = new TiledMapLoader();
        map = loader.load("scenes/DeathValley.tmx");
        mapObject = new GameObject("DeathValley");
        mapObject.addComponent(new TileMapRenderer(map));
        mapObject.addComponent(new ObjectLayerRenderer(map));
        addObject(mapObject);
        System.out.println("DeathValley loaded with map size: "+ map.width + "x" + map.height);

        //AABB
        mapBounds = new AABB(0, 0, map.getWidth(), map.getHeight());

        //Player
        PlayerPrefab playerPrefab = new PlayerPrefab();
        GameObject playerObj = playerPrefab.create(100, 100,getGame().getAssets().getTexture("player/orc8.png"));
        playerXP = playerObj.getComponent(PlayerXP.class);
        addObject(playerObj);
        
        //Slime
        Color xpColor = new Color(0.4f, 0f, 0.55f, 1f);
        SlimePrefab slimePrefab = new SlimePrefab();
        for (int i = 0; i < 10; i++) {
            final GameObject slimeRef = slimePrefab.create(200, 200, getGame().getAssets().getTexture("mob/slime.png"));
            slimeRef.getComponent(Health.class).setDeathListener((dx, dy, scene) -> {
                XPReward reward = slimeRef.getComponent(XPReward.class);
                float xp = reward != null ? reward.getAmount() : 0f;
                playerXP.addXP(xp);
                scene.addObject(FloatingTextPrefab.create(dx, dy + 10f, "+" + (int) xp + " XP", xpColor, 1.2f));
            });
            addObject(slimeRef);
        }

        // Camera
        CameraPrefab cameraPrefab = new CameraPrefab();
        GameObject cameraObj = cameraPrefab.create(playerObj.getComponent(Transform.class), getGame().getScreenWidth(), getGame().getScreenHeight());
        addObject(cameraObj);
        
        Camera camera = cameraObj.getComponent(Camera.class);
        camera.setWorldBounds(map.width * map.tileWidth, map.height * map.tileHeight);
        camera.setZoom(3);
        setCamera(camera);

    }

    public PlayerXP getPlayerXP() { return playerXP; }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
    
}

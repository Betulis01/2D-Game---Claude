package com.Betulis.Game2D.game.scenes;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.render.ObjectLayerRenderer;
import com.Betulis.Game2D.engine.render.TileMapRenderer;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.engine.tiled.TiledMapLoader;
import com.Betulis.Game2D.game.components.stats.PlayerXP;
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
        System.out.println("DeathValley loaded with map size: " + map.width + "x" + map.height);

        //AABB
        mapBounds = new AABB(0, 0, map.getWidth(), map.getHeight());

        //Player
        PlayerPrefab playerPrefab = new PlayerPrefab();
        GameObject playerObj = playerPrefab.create(100, 100, getGame().getAssets().getTexture("player/orc8.png"));
        playerXP = playerObj.getComponent(PlayerXP.class);
        addObject(playerObj);

        //Slimes
        SlimePrefab slimePrefab = new SlimePrefab();
        for (int i = 0; i < 10; i++) {
            addObject(slimePrefab.create(200, 200, getGame().getAssets().getTexture("mob/slime.png")));
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

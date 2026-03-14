package com.Betulis.Game2D.engine.system;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.render.SpriteRenderer;
import com.Betulis.Game2D.engine.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.SnapshotArray;
import java.util.Comparator;

public abstract class Scene {
    protected TiledMap map;
    protected AABB mapBounds;
    protected SnapshotArray<GameObject> objects;
    protected Game game;
    protected Camera camera;

    private final Comparator<GameObject> renderComparator = (a, b) -> {
        SpriteRenderer ra = a.getComponent(SpriteRenderer.class);
        SpriteRenderer rb = b.getComponent(SpriteRenderer.class);
        int layerA = ra != null ? ra.getSortLayer() : -1;
        int layerB = rb != null ? rb.getSortLayer() : -1;
        if (layerA != layerB) return Integer.compare(layerA, layerB);
        float yA = ra != null ? a.getTransform().getWorldY() - ra.getSortYOffset() : 0f;
        float yB = rb != null ? b.getTransform().getWorldY() - rb.getSortYOffset() : 0f;
        return Float.compare(yB, yA); // descending: higher Y renders first (behind)
    };

    /* LOAD */
    public void load(Game game) {
        this.game = game;
        objects = new SnapshotArray<>(GameObject.class);
        onLoad();
    }
    protected abstract void onLoad();

    /* UNLOAD */
    public void unload() {
        for (GameObject obj : objects) {
            obj.destroy();
        }
        objects.clear();
    }

    /* RENDER AND UPDATE */
    public void update(float dt) {
        GameObject[] items = objects.begin(); // Create the snapshot
        int n = objects.size; // size of the snapshot at begin()
        for (int i = 0; i < n; i++) { // Iterate using a standard for-loop (SnapshotArray is optimized for this)
            items[i].update(dt);
        }
        objects.end(); // Commit any additions/removals that happened during the loop
    }

    public void render(SpriteBatch batch) {
        objects.sort(renderComparator);
        GameObject[] items = objects.begin();
        int n = objects.size;
        for (int i = 0; i < n; i++) {
            items[i].render(batch);
        }
        objects.end();
    }

    /* ADD AND REMOVE */
    public void addObject(GameObject obj) {
        obj.setScene(this);
        objects.add(obj);
    }

    public void removeObject(GameObject obj) {
        objects.removeValue(obj, true); 
    }

    /* APIs */
    public GameObject getGameObjectByName(String name) {
        for (GameObject obj : objects) {
            if (obj.getName().equals(name)) {
                return obj;
            }
        }
        return null;
    }

    public TiledMap getMap() {
        return map;
    }

    public AABB getMapBounds() {
        return mapBounds;
    }

    public SnapshotArray<GameObject> getObjects() {
        return objects;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Game getGame() {
        return game;
    }

    public Camera getCamera() {
        return camera;
    }
}

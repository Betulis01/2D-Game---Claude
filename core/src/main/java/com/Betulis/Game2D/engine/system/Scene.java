package com.Betulis.Game2D.engine.system;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.render.LayeredSpriteRenderer;
import com.Betulis.Game2D.engine.render.SimpleAnimRenderer;
import com.Betulis.Game2D.engine.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.SnapshotArray;
import java.util.Comparator;

public abstract class Scene {
    protected TiledMap map;
    protected AABB mapBounds;
    protected SnapshotArray<GameObject> objects;
    protected SnapshotArray<GameObject> overlayObjects;
    protected Game game;
    protected Camera camera;

    private final Comparator<GameObject> renderComparator = (a, b) -> {
        SimpleAnimRenderer    ra = a.getComponent(SimpleAnimRenderer.class);
        SimpleAnimRenderer    rb = b.getComponent(SimpleAnimRenderer.class);
        LayeredSpriteRenderer la = (ra == null) ? a.getComponent(LayeredSpriteRenderer.class) : null;
        LayeredSpriteRenderer lb = (rb == null) ? b.getComponent(LayeredSpriteRenderer.class) : null;

        int layerA = ra != null ? ra.getSortLayer() : (la != null ? la.getSortLayer() : -1);
        int layerB = rb != null ? rb.getSortLayer() : (lb != null ? lb.getSortLayer() : -1);
        if (layerA != layerB) return Integer.compare(layerA, layerB);

        float yA = ra != null ? a.getTransform().getWorldY() - ra.getSortYOffset()
                 : la != null ? a.getTransform().getWorldY() - la.getSortYOffset() : 0f;
        float yB = rb != null ? b.getTransform().getWorldY() - rb.getSortYOffset()
                 : lb != null ? b.getTransform().getWorldY() - lb.getSortYOffset() : 0f;
        return Float.compare(yB, yA); // descending: higher Y renders first (behind)
    };

    /* LOAD */
    public void load(Game game) {
        this.game = game;
        objects = new SnapshotArray<>(GameObject.class);
        overlayObjects = new SnapshotArray<>(GameObject.class);
        onLoad();
    }
    protected abstract void onLoad();

    /* UNLOAD */
    public void unload() {
        for (GameObject obj : objects)        obj.destroy();
        for (GameObject obj : overlayObjects) obj.destroy();
        objects.clear();
        overlayObjects.clear();
    }

    /* RENDER AND UPDATE */
    public void update(float dt) {
        GameObject[] items = objects.begin();
        int n = objects.size;
        for (int i = 0; i < n; i++) {
            items[i].update(dt);
        }
        objects.end();

        GameObject[] ov = overlayObjects.begin();
        int on = overlayObjects.size;
        for (int i = 0; i < on; i++) {
            ov[i].update(dt);
        }
        overlayObjects.end();
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

    public void renderOverlay(SpriteBatch batch) {
        GameObject[] items = overlayObjects.begin();
        int n = overlayObjects.size;
        for (int i = 0; i < n; i++) {
            items[i].render(batch);
        }
        overlayObjects.end();
    }

    /* ADD AND REMOVE */
    public void addObject(GameObject obj) {
        obj.setScene(this);
        objects.add(obj);
    }

    public void addOverlayObject(GameObject obj) {
        obj.setScene(this);
        overlayObjects.add(obj);
    }

    public void removeObject(GameObject obj) {
        objects.removeValue(obj, true);
    }

    public void removeOverlayObject(GameObject obj) {
        overlayObjects.removeValue(obj, true);
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

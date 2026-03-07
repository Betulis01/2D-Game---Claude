package com.Betulis.Game2D.engine.camera;

import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.Transform;

public class Camera extends Component {

    private Transform target;

    private final float screenWidth;
    private final float screenHeight;
    private float zoom = 1.0f;
    private float lerp = 0.1f; // 0.1 means move 10% of the distance every frame. Lower = more "floaty/laggy", Higher = tighter following.

    private float worldWidth;
    private float worldHeight;
    private boolean clampToWorld = false;

    public Camera(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /* FOLLOW */

    public void follow(Transform target) {
        this.target = target;
    }

    /* WORLD CLAMP */
    public void setWorldBounds(float width, float height) {
        this.worldWidth = width;
        this.worldHeight = height;
        this.clampToWorld = true;
    }

    /* ZOOM */
    public void zoomBy(float delta) {
        setZoom(zoom + delta / 100);
    }

    public void setZoom(float zoom) {
        float minZoom = clampToWorld
            ? Math.max(screenWidth / worldWidth, screenHeight / worldHeight)
            : 0.25f;
        this.zoom = Math.max(minZoom, Math.min(zoom, 4.0f));
    }

    public float getZoom() {
        return zoom;
    }

    /* LERP */
    public void setLerp(float lerp) {
        this.lerp = lerp;
    }

    /* UPDATE */

    @Override
    public void update(float dt) {
        if (target == null) return;
        float actualLerp = 1.0f - (float) Math.pow(1.0f - lerp, dt * getScene().getGame().getFps());

        float currentX = (float) transform.getWorldX();
        float currentY = (float) transform.getWorldY();

        float targetX = (float) target.getWorldX();
        float targetY = (float) target.getWorldY();

        float nextX = currentX + (targetX - currentX) * actualLerp;
        float nextY = currentY + (targetY - currentY) * actualLerp;


        if (clampToWorld) {
            float viewW = screenWidth / zoom;
            float viewH = screenHeight / zoom;
            float halfW = viewW * 0.5f;
            float halfH = viewH * 0.5f;

            nextX = clamp(nextX, halfW, worldWidth - halfW);
            nextY = clamp(nextY, halfH, worldHeight - halfH);
        }

        transform.setPosition(nextX, nextY);
    }


    /* VIEW RECT */
    private final AABB viewBounds = new AABB(0,0,0,0);

    public AABB getViewBounds() {
        float viewW = screenWidth / zoom;
        float viewH = screenHeight / zoom;

        viewBounds.x = transform.getWorldX() - viewW * 0.5f;
        viewBounds.y = transform.getWorldY() - viewH * 0.5f;
        viewBounds.width  = viewW;
        viewBounds.height = viewH;

        return viewBounds;
    }


    /* COORD CONVERSION */

    public float worldToScreenX(float worldX) {
        return (worldX - (transform.getWorldX() - screenWidth / (2 * zoom))) * zoom;
    }

    public float worldToScreenY(float worldY) {
        return (worldY - (transform.getWorldY() - screenHeight / (2 * zoom))) * zoom;
    }

    public float screenToWorldX(float screenX) {
        return screenX / zoom + (transform.getWorldX() - screenWidth / (2 * zoom));
    }

    public float screenToWorldY(float screenY) {
        float flipped = screenHeight - screenY; // screenHeight should match window/viewport height
        return flipped / zoom + (transform.getWorldY() - screenHeight / (2f * zoom));
    }

    /* UTIL */

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(v, max));
    }
}

package com.Betulis.Game2D.game.components.movement;

import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.render.SpriteRenderer;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.components.AABB.Hitbox;

public final class EntityMover extends Component {

    private Movement movement;
    private SpriteRenderer sprite;  

    private float speed;

    public EntityMover(float speed) {
        this.speed = speed;
    }

    @Override
    public void start() {
        transform = gameObject.getComponent(Transform.class);
        movement  = gameObject.getComponent(Movement.class);
        sprite    = gameObject.getComponent(SpriteRenderer.class);
    }

    @Override
    public void update(float dt) {
        if (movement == null || sprite == null) return;

        movement.updateMovement(dt);
        if (!movement.isMoving()) return;

        Vector2 dir = movement.getDirection();
        float dx = dir.x * speed * dt;
        float dy = dir.y * speed * dt;

        float nextX = transform.getX() + dx;
        float nextY = transform.getY() + dy;

        float halfW = sprite.getWidth()  * 0.5f;
        float halfH = sprite.getHeight() * 0.5f;

        
        float mapWidth  = getScene().getMap().getWidth();
        float mapHeight = getScene().getMap().getHeight();

        if (getGameObject().getComponent(Hitbox.class) == null) {
            // Clamp the CENTER
            if (nextX < halfW) nextX = halfW;
            if (nextX > mapWidth - halfW) nextX = mapWidth - halfW;

            if (nextY < halfH) nextY = halfH;
            if (nextY > mapHeight - halfH) nextY = mapHeight - halfH;
        }


        transform.setPosition(nextX, nextY);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public Movement getMovement() {
        return movement;
    }
}

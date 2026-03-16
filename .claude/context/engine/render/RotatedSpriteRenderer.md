# RotatedSpriteRenderer

**File:** `core/.../engine/render/RotatedSpriteRenderer.java`
**Extends/Type:** `extends SimpleAnimRenderer`
**Role:** `SimpleAnimRenderer` variant that applies transform rotation when drawing. Used by projectile attacks (Fireball, LightningBolt, SlimeSpit).

## Key Methods / Logic
- Constructor: `RotatedSpriteRenderer(float width, float height)` — delegates to `super`.
- `render(batch)`: calls `getCurrentFrame()` (inherited protected method), then `batch.draw(frame, x, y, originX, originY, w, h, 1, 1, rotation)` using `transform.getWorldRotation()`.
- Inherits all clip management, `play()`, `isFinished()`, sort accessors from `SimpleAnimRenderer`.

## Dependencies
- `SimpleAnimRenderer` (parent)
- `Camera` (via `getScene().getCamera()`)
- `transform.getWorldRotation()`

## Rules
- No own fields — all state lives in `SimpleAnimRenderer`.
- `Scene.renderComparator` catches it via the `SimpleAnimRenderer` check automatically.

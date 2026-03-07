# RotatedSpriteRenderer

**File:** `core/.../engine/render/RotatedSpriteRenderer.java`
**Extends:** `SpriteRenderer`
**Role:** Same as SpriteRenderer but applies `transform.rotation` when drawing. Used for projectiles.

## Render Logic
```
batch.draw(frame, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
// origin = center of sprite for correct rotation pivot
```

## Difference from SpriteRenderer
- Uses 9-parameter `batch.draw()` overload with rotation
- Origin point set to sprite center so rotation pivots correctly

## Rules
- Rotation in degrees, taken from `transform.rotation`
- All other rules identical to SpriteRenderer (center-anchored, read-only, camera-transformed)

# SpriteRenderer

**File:** `core/.../engine/render/SpriteRenderer.java`
**Extends:** `Component`
**Role:** Renders the current animation frame centered on the transform, in world space projected through the camera.

## Dependencies
- `AnimationDirector` — provides current clip
- `AnimationUpdater` — provides current frameIndex
- `Camera` (via scene) — world→screen coordinate conversion
- `Transform` — world position

## Render Logic
```
frame = director.getCurrentClip().getFrame(updater.frameIndex, row)
screenX = camera.worldToScreenX(transform.worldX) - (frame.width * zoom) / 2
screenY = camera.worldToScreenY(transform.worldY) - (frame.height * zoom) / 2
batch.draw(frame, screenX, screenY, width * zoom, height * zoom)
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `row` | `int` | Which sprite sheet row to render (direction) |
| `director` | `AnimationDirector` | Animation state |
| `updater` | `AnimationUpdater` | Frame index timer |

## Rules
- Sprite is always centered on `transform.worldX/Y`
- Rendering never affects simulation — read-only access to transform
- `row` is set externally by animation controllers (e.g., PlayerAnimation)

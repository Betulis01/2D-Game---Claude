# Camera

**File:** `core/.../engine/camera/Camera.java`
**Extends:** `Component`
**Role:** Rendering service. Follows a target transform with lerp smoothing, clamps to world bounds, supports zoom. Provides world↔screen coordinate conversion.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `x`, `y` | `float` | Camera center in world space |
| `zoom` | `float` | Render scale (0.25 – 4.0) |
| `lerp` | `float` | Follow smoothness (0–1, default 0.1) |
| `target` | `Transform` | GameObject to follow |
| `worldBoundsW/H` | `float` | Map pixel dimensions for clamping |
| `screenWidth/Height` | `int` | Viewport size |
| `viewBounds` | `AABB` | Visible world region (for culling) |

## Follow Logic (frame-rate independent lerp)
```java
actualLerp = 1.0 - pow(1.0 - lerp, dt * 60)
x += (target.worldX - x) * actualLerp
y += (target.worldY - y) * actualLerp
// then clamp to world bounds
```

## Coordinate Conversion
```java
screenX = (worldX - x) * zoom + screenWidth / 2
screenY = (worldY - y) * zoom + screenHeight / 2  // Y-flipped

worldX = (screenX - screenWidth / 2) / zoom + x
worldY = (screenY - screenHeight / 2) / zoom + y  // Y-flipped
```

## Key Methods
- `setTarget(Transform)` — sets follow target
- `setZoom(float)` — clamped to 0.25–4.0
- `zoomBy(float delta)` — incremental zoom (used by CameraZoom)
- `setWorldBounds(float w, float h)` — prevents camera from showing outside map
- `worldToScreenX/Y(float)` — used by all renderers
- `screenToWorldX/Y(float)` — used by PlayerInput for mouse→world conversion
- `updateViewBounds()` — recalculates visible AABB for culling

## Rules
- Camera never affects simulation (movement, collision, combat)
- Zoom affects rendering only
- One camera per scene
- Y-axis is flipped in screen space — all coord conversion must account for this

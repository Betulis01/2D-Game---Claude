# FloatingText

**File:** `core/.../game/components/FloatingText.java`
**Extends:** `Component`
**Role:** Displays a world-anchored text label that rises upward and fades out over a set duration, then destroys its GameObject.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `text` | `String` | The string to display |
| `color` | `Color` | Base color (alpha overridden per-frame by fade) |
| `duration` | `float` | Total display time in seconds |
| `riseSpeed` | `float` | World-units per second upward drift (20f) |
| `elapsed` | `float` | Time since creation |
| `font` | `BitmapFont` | Default LibGDX bitmap font, created in constructor |

## Key Methods
- Constructor — creates `BitmapFont` immediately (not in start()) to survive same-frame render
- `update(dt)` — increments elapsed; destroys GameObject when elapsed ≥ duration
- `render(SpriteBatch)` — converts world pos + rise offset to screen via `camera.worldToScreenX/Y()`; sets alpha = 1 - (elapsed/duration)
- `onDestroy()` — disposes font

## Coordinate Conversion
```java
float wy = transform.getWorldY() + riseSpeed * elapsed;
float sx = cam.worldToScreenX(wx);
float sy = cam.worldToScreenY(wy);
```
Same pattern as HealthRenderer.

## Why Font Created in Constructor
`render()` can be called before the first `update()` (same frame as addObject). `start()` runs from `update()` only, so font must be ready before start() fires.

## Rules
- Always created via `FloatingTextPrefab.create()` — never constructed manually
- Self-destructs — no external cleanup needed
- Font disposed in `onDestroy()` to prevent memory leak

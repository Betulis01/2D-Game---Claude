# CameraZoom

**File:** `core/.../engine/camera/CameraZoom.java`
**Extends:** `Component`
**Role:** Reads scroll input from InputBindings and applies it to the Camera as a zoom delta each frame.

## Update Logic
```
scrollDelta = inputBindings.getScrollDelta()
if scrollDelta != 0: camera.zoomBy(scrollDelta * zoomSpeed)
```

## Dependencies
- `InputBindings` — scroll delta
- `Camera` (sibling component) — `zoomBy()`

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `zoomSpeed` | `float` | Multiplier applied to raw scroll delta |

## Rules
- Scroll delta must be consumed (cleared) each frame — handled by InputBindings
- Zoom bounds (0.25–4.0) enforced by Camera, not CameraZoom

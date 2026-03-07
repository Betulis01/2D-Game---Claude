# DebugRender

**File:** `core/.../engine/render/DebugRender.java`
**Extends:** `Component`
**Role:** Overlays debug information on screen. Always renders FPS. Shows position, tile coords, hitbox/hurtbox AABBs when DEBUG key is held.

## What It Renders
| Info | Condition |
|------|-----------|
| FPS | Always |
| Player world position | DEBUG key held |
| Player tile coordinates | DEBUG key held |
| Hitbox AABBs (red outlines) | DEBUG key held |
| Hurtbox AABBs (green outlines) | DEBUG key held |

## Render Technique
- Uses `Assets.whitePixel` (1×1 white Texture) drawn at full rect size to create colored rectangles
- Rendered in screen space — no camera transform applied to the overlay text/shapes

## Dependencies
- `InputBindings` — reads DEBUG key state
- `Scene` — iterates all GameObjects to find Hitbox/Hurtbox components
- `Assets` — white pixel texture for rectangle drawing

## Rules
- DebugRender always renders last (highest layer)
- Debug shapes are screen-space projections of world-space AABB bounds
- This component is tightly coupled to game-specific types — acceptable for debug tooling only

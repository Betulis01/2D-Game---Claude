# EntityMover

**File:** `core/.../game/components/movement/EntityMover.java`
**Extends:** `Component`
**Role:** Integrates a Movement component's direction into actual Transform position each frame. Clamps position to map bounds.

## Update Logic
```
direction = movement.direction
transform.worldX += direction.x * speed * dt
transform.worldY += direction.y * speed * dt

// Clamp to map bounds (center-anchored):
halfW = spriteWidth / 2
halfH = spriteHeight / 2
worldX = clamp(worldX, halfW, mapWidth - halfW)
worldY = clamp(worldY, halfH, mapHeight - halfH)
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `speed` | `float` | World units per second (from EntityConfig) |
| `spriteWidth/Height` | `float` | Used to calculate half-extents for clamping |

## Dependencies
- `Movement` (sibling) — direction source
- `Scene.getMapBounds()` — world bounds for clamping

## Rules
- Speed comes from EntityConfig — not hardcoded
- Clamping uses half-extents: `min = halfSize`, `max = mapSize - halfSize`
- Projectiles skip bounds clamping (destroyed by AttackOutsideMapDespawner instead)

## Known Issues
- Half-width used for clamping assumes centered sprite — breaks if sprite origin is off-center

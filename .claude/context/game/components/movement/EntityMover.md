# EntityMover

**File:** `core/.../game/components/movement/EntityMover.java`
**Extends:** `Component`
**Role:** Integrates the active Movement component's direction into actual Transform position each frame. Clamps position to map bounds. Acts as the single source of truth for movement state — animation components read from here, not from individual Movement components.

## Update Logic
```
movement.updateMovement(dt)
if !movement.isMoving(): return

direction = movement.getDirection()
nextX = transform.x + direction.x * speed * dt
nextY = transform.y + direction.y * speed * dt

// Clamp to map bounds (center-anchored, skip if entity has Hitbox):
halfW = spriteWidth / 2
halfH = spriteHeight / 2
nextX = clamp(nextX, halfW, mapWidth - halfW)
nextY = clamp(nextY, halfH, mapHeight - halfH)

transform.setPosition(nextX, nextY)
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `speed` | `float` | World units per second |
| `movement` | `Movement` | Currently active movement behavior (swappable at runtime) |
| `sprite` | `SpriteRenderer` | Half-extents for bounds clamping |

## Key Methods
| Method | Purpose |
|--------|---------|
| `setMovement(Movement)` | Swap the active movement behavior at runtime |
| `getMovement()` | Returns currently active movement (used by SlimeAI for identity check) |
| `isMoving()` | Delegates to `movement.isMoving()` — animation reads from here |
| `getDirection()` | Delegates to `movement.getDirection()` — animation reads from here |
| `setSpeed(float)` | Called by AI on state transition to match behavior speed |

## Dependencies
- `Movement` (sibling, active) — direction and moving state
- `SpriteRenderer` (sibling) — sprite dimensions for clamping
- `Scene.getMap()` — world bounds

## Rules
- Animation components must read `isMoving()` / `getDirection()` from EntityMover, NOT from any specific Movement subclass
- Clamping skipped for entities with a Hitbox (projectiles use AttackOutsideMapDespawner instead)
- Clamping uses half-extents — assumes centered sprite origin

## Known Issues
- Half-width clamping breaks if sprite origin is off-center

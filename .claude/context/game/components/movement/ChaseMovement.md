# ChaseMovement

**File:** `core/.../game/components/movement/ChaseMovement.java`
**Extends:** `Movement`
**Role:** Steers directly toward a target Transform each frame. Stops when within stopDistance. Exposes `shouldGiveUp()` for the AI controller to poll.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `spriteWidth` | `float` | Stop distance threshold (entity stops when dist ≤ spriteWidth) |
| `speed` | `float` | Movement speed, exposed via `getSpeed()` |
| `range` | `float` | Set in `start()` as `spriteWidth * 10` — give-up distance |
| `target` | `Transform` | The transform being chased; set via `setTarget()` |

## Key Logic
```
start():
  range = spriteWidth * 10   // derived from sprite, not passed in

updateMovement(dt):
  if target == null → moving = false, return
  dist = distance to target
  if dist <= spriteWidth → moving = false (stop, wait for attack)
  else → direction = normalize(target - self), moving = true

shouldGiveUp():
  true if: target == null
        OR target.Health.isDead()
        OR target.CombatState not in combat
        OR dist > range
```

## Dependencies
- `SpriteRenderer` (sibling) — `range` derived from width in `start()`
- `Health` (on target) — dead check
- `CombatState` (on target) — combat state check

## Rules
- `range` must not be set in field initializer — `getGameObject()` is null there; use `start()`
- Does not transition states itself — `shouldGiveUp()` is polled by `SlimeAI`
- Speed owned here, exposed via `getSpeed()`

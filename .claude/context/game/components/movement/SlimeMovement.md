# SlimeMovement

**File:** `core/.../game/components/movement/SlimeMovement.java`
**Extends:** `Movement`
**Role:** Simple random-wander AI. Picks a new random direction every N seconds.

## Update Logic
```
timer += dt
if timer >= changeInterval:
  timer = 0
  pick random angle → direction = (cos, sin), normalized
  moving = true
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `changeInterval` | `float` | Seconds between direction changes |
| `timer` | `float` | Elapsed time since last direction change |

## Rules
- `changeInterval` should come from EntityConfig — currently hardcoded at 2.0s
- Direction is always normalized
- Entity never stops (no idle state in this AI)

## Known Issues
- `changeInterval` hardcoded at 2.0f — must be moved to EntityConfig

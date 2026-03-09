# WanderMovement

**File:** `core/.../game/components/movement/WanderMovement.java`
**Extends:** `Movement`
**Role:** Craig Reynolds' circle-ahead wander steering behaviour with idle pauses and a home radius. Produces smooth arcing paths rather than direction snaps.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `speed` | `float` | Movement speed (passed in constructor, exposed via `getSpeed()`) |
| `wanderAngle` | `float` | Current angle of the wander point on the projected circle (radians) |
| `phase` | `Phase` | `MOVING` or `IDLE` |
| `phaseTimer` | `float` | Countdown to next phase transition |
| `homeX/homeY` | `float` | Spawn position — captured in `start()` |

## Constants
| Constant | Value | Purpose |
|----------|-------|---------|
| `WANDER_DISTANCE` | `80f` | How far ahead the circle is projected |
| `WANDER_RADIUS` | `40f` | Circle radius — larger = wider turns |
| `WANDER_JITTER` | `2.0f` | Max angular displacement per second (rad/s) |
| `MOVE_MIN/MAX` | `3–7s` | Moving phase duration range |
| `IDLE_MIN/MAX` | `2–10s` | Idle phase duration range |
| `HOME_RADIUS` | `100f` | Max wander distance from spawn |

## Key Logic
```
MOVING phase:
  wanderAngle += random(-1,1) * WANDER_JITTER * dt
  circleCenter = direction * WANDER_DISTANCE
  wanderPoint  = circleCenter + (cos(wanderAngle), sin(wanderAngle)) * WANDER_RADIUS
  direction    = normalize(wanderPoint)
  moving = true

IDLE phase:
  moving = false
  (timer counts down, then picks fresh wanderAngle and returns to MOVING)

HOME check (runs before phase logic):
  if dist(pos, home) > HOME_RADIUS:
    steer straight back to homeX/homeY
    sync wanderAngle to return direction (smooth re-entry)
```

## Dependencies
- `gameObject.getTransform()` — current position and home capture in `start()`

## Rules
- `speed` is owned here and exposed via `getSpeed()` — SlimeAI reads it to set EntityMover speed on transition
- `start()` must run after Transform is positioned (it is — prefab sets position before adding components)
- Does not know about EntityMover or SlimeAI

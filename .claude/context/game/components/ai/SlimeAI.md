# SlimeAI

**File:** `core/.../game/components/ai/SlimeAI.java`
**Extends:** `Component`
**Role:** State machine that switches EntityMover between WanderMovement and ChaseMovement based on aggro events and give-up conditions.

## States
| State | Active Movement | Trigger In | Trigger Out |
|-------|----------------|------------|-------------|
| WANDER | `WanderMovement` | start, give-up | `aggro()` called |
| CHASE | `ChaseMovement` | `aggro()` | `chase.shouldGiveUp()` |

## Key Methods
| Method | Purpose |
|--------|---------|
| `aggro(Transform attacker)` | Sets chase target, switches EntityMover to ChaseMovement at chase speed |
| `enterWander()` | Clears chase target, switches EntityMover to WanderMovement at wander speed |

## Update Logic
```
if active movement == chase AND chase.shouldGiveUp():
  enterWander()
```

## Dependencies
- `WanderMovement` (sibling) — wander behavior + speed
- `ChaseMovement` (sibling) — chase behavior + speed + shouldGiveUp()
- `EntityMover` (sibling) — receives setMovement() and setSpeed() calls

## Rules
- Does not hardcode speeds — reads them from `wander.getSpeed()` / `chase.getSpeed()`
- `aggro()` is called by `DamageOnHit` when this entity is struck
- AI and movement are fully decoupled: SlimeAI swaps behaviors, behaviors don't know about AI

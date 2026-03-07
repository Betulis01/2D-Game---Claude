# CombatState

**File:** `core/.../game/components/combat/CombatState.java`
**Extends:** `Component`
**Role:** Tracks whether an entity is actively in combat. Health bars and other combat-conditional UI are gated on this flag.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `inCombat` | `boolean` | Current combat status |
| `lastCombatTime` | `float` | Timestamp of last combat event |
| `exitDelay` | `float` | Seconds of inactivity before exiting combat |

## Update Logic
```
if inCombat and (now - lastCombatTime) > exitDelay:
  inCombat = false
```

## Key Methods
- `enterCombat()` — sets `inCombat = true`, records timestamp
- `isInCombat()` — read by HealthRenderer and other UI components

## Rules
- `exitDelay` comes from EntityConfig — not hardcoded
- Called by DamageOnHit on both attacker's owner and the victim

## Known Issues
- `exitDelay` currently hardcoded at 10.0f — must come from EntityConfig

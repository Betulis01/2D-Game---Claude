# AttackDurationDespawner

**File:** `core/.../game/components/combat/AttackDurationDespawner.java`
**Extends:** `Component`
**Role:** Destroys an attack entity after its maximum lifetime expires without hitting anything. Plays a "noHit" animation briefly before despawning.

## Update Logic
```
durationTimer += dt
if durationTimer >= maxDuration:
  director.play("noHit", false)
  schedule destroy after noHit animation finishes (via AnimationAutoDespawner or timer)
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `maxDuration` | `float` | Max seconds before forced despawn (from EntityConfig) |
| `durationTimer` | `float` | Elapsed time |

## Dependencies
- `AnimationDirector` (sibling) — switches to "noHit" clip on expiry
- `AnimationAutoDespawner` — destroys the object after the "noHit" clip finishes

## Rules
- Duration comes from EntityConfig — not hardcoded
- If DamageOnHit fires first, it destroys the object before this timer triggers — no conflict
- "noHit" clip must be registered on the AnimationDirector at prefab construction time

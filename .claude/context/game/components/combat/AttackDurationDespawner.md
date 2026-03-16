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
- `SimpleAnimRenderer` (sibling) — switches to "noHit" clip on expiry (null-safe)

## Rules
- Duration comes from EntityConfig — not hardcoded
- If DamageOnHit fires first, it destroys the object before this timer triggers — no conflict
- "noHit" clip play is a best-effort: if clip not registered, it's silently skipped
- Destroys self via internal 0.5s `despawnTimer` after duration expires

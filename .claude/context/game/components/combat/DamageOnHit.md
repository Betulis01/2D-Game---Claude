# DamageOnHit

**File:** `core/.../game/components/combat/DamageOnHit.java`
**Extends:** `Component`
**Role:** On each frame, checks if this entity's Hitbox overlaps any Hurtbox in the scene. On hit: applies damage, spawns hit effect, destroys self.

## Update Logic
```
for each GameObject in scene:
  hurtbox = obj.getComponent(Hurtbox.class)
  if hurtbox == null: continue
  if this.hitbox.worldBounds.intersects(hurtbox.worldBounds):
    hurtbox.gameObject.getComponent(Health.class).applyDamage(damage)
    onHit(hitPosition)   // spawns explosion effect
    gameObject.destroy() // destroy attack entity
    return               // stop after first hit
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `damage` | `int` | Flat damage value (from EntityConfig) |

## onHit()
- Spawns a FireballExplosion effect at impact position
- Sets CombatState on both attacker owner and victim

## Dependencies
- `Hitbox` (sibling) — source bounds
- `Scene` — iterates all objects to find Hurtboxes
- `Health` (on victim) — applies damage
- `CombatState` (on both) — flags combat
- `FireballExplosion` prefab — spawns hit effect

## Rules
- Destroy self after first hit — attacks do not pierce by default
- Damage value from EntityConfig — no hardcoded numbers
- O(n) per attacker, O(n²) total — acceptable up to ~50 entities

## Known Issues
- Iterates all scene GameObjects every frame — no spatial partitioning
- No faction/team filtering — all hitboxes hit all hurtboxes
- Hit effect is hardcoded to FireballExplosion — should be configurable per attack type

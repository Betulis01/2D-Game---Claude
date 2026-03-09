# DamageOnHit

**File:** `core/.../game/components/combat/DamageOnHit.java`
**Extends:** `Component`
**Role:** On each frame, checks if this entity's Hitbox overlaps any Hurtbox in the scene. On hit: applies damage, notifies mob AI, spawns hit effect, destroys self.

## Update Logic
```
for each GameObject in scene:
  if target == owner: skip
  hurtbox = target.getComponent(Hurtbox.class)
  if hurtbox == null: skip
  if this.hitbox.worldBounds.intersects(hurtbox.worldBounds):
    target.Health.applyDamage(damage)
    onHit(target)
    return
```

## onHit()
```
owner.CombatState.enterCombat()
target.CombatState.enterCombat()

SlimeAI ai = target.getComponent(SlimeAI.class)
if ai != null: ai.aggro(owner.getTransform())   // notify mob AI

spawnExplosion(target)   // parents FireballExplosion to target transform
gameObject.destroy()
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `owner` | `GameObject` | The entity that fired this attack (used for aggro source and faction skip) |
| `dmg` | `float` | Flat damage value from EntityConfig |

## Dependencies
- `Hitbox` (sibling) — source bounds
- `Scene` — iterates all objects to find Hurtboxes
- `Health` (on victim) — applies damage
- `CombatState` (on both) — flags combat
- `SlimeAI` (on victim, optional) — aggro notification
- `FireballExplosion` prefab — spawns hit effect

## Rules
- Destroy self after first hit — attacks do not pierce by default
- Aggro notification is null-checked — safe for non-slime targets
- Damage value from EntityConfig — no hardcoded numbers

## Known Issues
- Iterates all scene GameObjects every frame — no spatial partitioning
- No faction/team filtering — all hitboxes hit all hurtboxes
- Hit effect hardcoded to FireballExplosion — should be configurable per attack type

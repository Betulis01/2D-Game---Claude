# FireballExplosion

**File:** `core/.../game/prefabs/attacks/FireballExplosion.java`
**Role:** Factory for a one-shot explosion visual effect that follows the hit target for its duration. Self-destructs when animation ends.

## Produced GameObject: "FireballExplosion"
| Component | Purpose |
|-----------|---------|
| `Transform` | Parented to owner's Transform — follows target position automatically |
| `RotatedSpriteRenderer` | Draws explosion frames; `clipFromAtlas(fireballAtlas, "explode", dur)`, `play("explode", false)` |
| `AudioPlayer("fireball_explosion")` | Plays explosion sound spatially on first frame |
| `AnimationAutoDespawner` | Destroys when animation finishes |

## Config
- Loads `data/config/abilities/fireball_explosion.json`
- All sprite dimensions and `frameDuration` from config — no hardcoded values

## Transform Parenting
```
effect.getTransform().setParent(owner.getTransform())
// local position (0,0) → effect tracks owner world position each frame
```

## Rules
- Pure visual — no hitbox, no damage
- Spawned by `DamageOnHit.spawnExplosion()` — position handled by parenting, not manual setWorldPosition
- Self-cleaning via AnimationAutoDespawner
- Uses `RotatedSpriteRenderer` (not `SpriteRenderer`) — consistent with attack visuals

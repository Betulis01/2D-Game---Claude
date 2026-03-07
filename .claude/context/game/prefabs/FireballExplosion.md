# FireballExplosion

**File:** `core/.../game/prefabs/attacks/FireballExplosion.java`
**Role:** Factory for a one-shot explosion visual effect spawned at hit position. Self-destructs when animation ends.

## Produced GameObject: "FireballExplosion"
| Component | Purpose |
|-----------|---------|
| `Transform` | Positioned at impact point |
| `AnimationDirector` | Single "explode" clip |
| `AnimationUpdater` | Frame timer |
| `SpriteRenderer` | Draws explosion frames |
| `AnimationAutoDespawner` | Destroys when animation finishes |

## Animation
- Single non-looping "explode" clip
- AnimationAutoDespawner removes the GameObject after last frame plays

## Rules
- Pure visual — no hitbox, no damage
- Spawned by DamageOnHit.onHit() at the impact world position
- Self-cleaning via AnimationAutoDespawner — no manual cleanup needed

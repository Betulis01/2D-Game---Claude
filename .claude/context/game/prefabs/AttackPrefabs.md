# AttackPrefabs

**File:** `core/.../game/prefabs/attacks/AttackPrefabs.java`
**Role:** Static factory methods for all attack entity types. Constructs ephemeral attack GameObjects spawned into the scene by AttackSpawner.

## Methods

### `createFireball(scene, origin, dirX, dirY, cfg)`
**GameObject: "Fireball"**
| Component | Purpose |
|-----------|---------|
| `Transform` | Spawned at origin entity's position |
| `Projectile` | Fixed direction movement |
| `EntityMover` | Applies projectile speed |
| `RotatedSpriteRenderer` | Draws sprite rotated to face direction |
| `AnimationDirector/Updater` | Clip playback |
| `Hitbox` | Offensive bounds |
| `DamageOnHit` | Hit detection → damage |
| `AttackDurationDespawner` | Destroys after max duration |
| `AttackOutsideMapDespawner` | Destroys when leaving map |

### `createLightningBolt(scene, origin, dirX, dirY, cfg)`
**Identical component setup to Fireball** — different sprite and stats from cfg.

## Rules
- All stats (speed, damage, duration) from EntityConfig cfg parameter
- No hardcoded numbers in prefab setup
- Both attack types share a `createProjectile()` base method to avoid duplication

## Known Issues
- `createFireball` and `createLightningBolt` are mostly duplicated — extract shared `createProjectile()` base
- Hit effect hardcoded to FireballExplosion in DamageOnHit — should be configurable per attack type

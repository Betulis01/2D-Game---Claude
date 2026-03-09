# AttackPrefabs

**File:** `core/.../game/prefabs/attacks/AttackPrefabs.java`
**Role:** Static factory methods for all attack entity types. Constructs ephemeral attack GameObjects spawned into the scene by AttackSpawner.

## Methods

### `createFireball(cfg, owner, dir, asset)`
**GameObject: "Fireball"**
| Component | Purpose |
|-----------|---------|
| `Transform` | Spawned at owner's world position, rotated to face direction |
| `Projectile` | Fixed direction movement |
| `EntityMover` | Applies projectile speed from cfg |
| `RotatedSpriteRenderer` | Draws sprite rotated to face direction (size from cfg.sprite) |
| `AnimationDirector/Updater` | Clip playback — frameDuration and frame count from cfg |
| `Hitbox` | Offensive bounds from cfg.hitbox |
| `DamageOnHit` | Hit detection → damage |
| `AttackDurationDespawner` | Destroys after cfg.stats.duration |
| `AttackOutsideMapDespawner` | Destroys when leaving map |

### `createLightningBolt(cfg, owner, dir, asset)`
Identical component setup to Fireball — different sprite and stats from its own cfg.

## Rules
- All stats (speed, damage, duration) and all sprite dimensions from `EntityConfig cfg` parameter
- `SpriteSheetSlicer` uses `cfg.sprite.frames` and `cfg.sprite.directions`
- `RotatedSpriteRenderer` uses `cfg.sprite.width/height`
- Animation end frame = `cfg.sprite.frames - 1` — no hardcoded frame indices
- `frameDuration` from `cfg.sprite.frameDuration`

## Known Issues
- `createFireball` and `createLightningBolt` bodies are mostly duplicated — extract shared `createProjectile()` base

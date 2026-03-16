# AttackPrefabs

**File:** `core/.../game/prefabs/attacks/AttackPrefabs.java`
**Role:** Static factory methods for all attack entity types. Constructs ephemeral attack GameObjects spawned into the scene by AttackSpawner.

## Methods

### `createFireball(cfg, owner, dir, assets)`
**GameObject: "Fireball"**
| Component | Purpose |
|-----------|---------|
| `Transform` | Spawned at owner's world position, rotated to face direction |
| `Projectile` | Fixed direction movement |
| `EntityMover` | Applies projectile speed from cfg |
| `RotatedSpriteRenderer` | Draws sprite rotated to face direction; `clipFromAtlas(fireballAtlas, "_fly", dur)` |
| `Hitbox` | Offensive bounds from cfg.hitbox |
| `DamageOnHit` | Hit detection → damage |
| `AudioPlayer` (0..N) | One per `cfg.spawnSounds` entry |
| `AttackDurationDespawner` | Destroys after cfg.stats.duration |
| `AttackOutsideMapDespawner` | Destroys when leaving map |

### `createLightningBolt(cfg, owner, dir, assets)`
Identical setup to Fireball — `clipFromAtlas(lightningAtlas, "_fly", dur)`.

### `createSlimeSpit(cfg, owner, dir, assets)`
Identical setup — `clipFromAtlas(spitAtlas, "_fly", dur)`.

## Rules
- All stats and sprite dimensions from `EntityConfig cfg`
- All projectiles use `RotatedSpriteRenderer` + `clipFromAtlas()`
- Sort layer 3 for all attack sprites
- No `AnimationDirector`, `AnimationUpdater`, or `SpriteSheetSlicer` — all replaced by atlas pipeline

# SlimePrefab

**File:** `core/.../game/prefabs/mobs/SlimePrefab.java`
**Role:** Factory that constructs a slime enemy GameObject with Reynolds wander AI and aggro-chase behaviour.

## Produced GameObject: "Slime"
| Component | Purpose |
|-----------|---------|
| `Transform` | Position |
| `WanderMovement` | Reynolds circle-ahead wander (speed = moveSpeed × 0.8) |
| `ChaseMovement` | Direct pursuit (speed = moveSpeed × 1.2, range = spriteWidth × 10) |
| `EntityMover` | Active movement → position |
| `SlimeAI` | State machine: wander ↔ chase transitions |
| `AnimationDirector` | Clip registry |
| `AnimationUpdater` | Frame timer |
| `SpriteRenderer` | Draws current frame |
| `SlimeAnimation` | moving/idle → clip switch (reads EntityMover) |
| `Health` | HP from EntityConfig |
| `CombatState` | Combat tracking |
| `Hurtbox` | Receives damage |
| `HealthRenderer` | World-space health bar |

## Speed Derivation
```
wanderSpeed = cfg.stats.moveSpeed * 0.8
chaseSpeed  = cfg.stats.moveSpeed * 1.2
```

## Animation Clips Registered
- `jump` (looping) — played while moving
- `idle` (looping) — played while stationary

## Rules
- All stats and dimensions from `EntityConfig` (slime.json)
- `SpriteSheetSlicer` uses `cfg.sprite.frames` and `cfg.sprite.directions` — no hardcoded sheet layout
- `frameDuration` from `cfg.sprite.frameDuration`
- Wander and chase speeds are derived from `moveSpeed` — only one speed value in JSON

## Known Issues
- Sprite sheet uses 1 direction row — no directional slime animation

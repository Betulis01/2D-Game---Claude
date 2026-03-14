# SlimePrefab

**File:** `core/.../game/prefabs/mobs/SlimePrefab.java`
**Role:** Factory that constructs a slime enemy GameObject with Reynolds wander AI, aggro-chase behaviour, XP reward, and two loot drop rolls.

## Produced GameObject: "Slime"
| Component | Purpose |
|-----------|---------|
| `Transform` | Position |
| `WanderMovement` | Reynolds circle-ahead wander (speed = moveSpeed × 0.8) |
| `ChaseMovement` | Direct pursuit (speed = moveSpeed × 1.2) |
| `EntityMover` | Active movement → position |
| `SlimeAI` | State machine: wander ↔ chase transitions |
| `AnimationDirector` | Clip registry |
| `AnimationUpdater` | Frame timer |
| `SpriteRenderer` | Draws current frame |
| `SlimeAnimation` | moving/idle → clip switch |
| `Health` | HP from EntityConfig |
| `HealthRenderer` | World-space health bar |
| `CombatState` | Combat tracking |
| `Hurtbox` | Receives damage |
| `XPReward` | Self-wires death listener; awards XP + spawns floating text |
| `LootDropper` (×2) | 40% small-sword, 40% blue-ring — independent rolls |

## Speed Derivation
```
wanderSpeed = cfg.stats.moveSpeed * 0.8
chaseSpeed  = cfg.stats.moveSpeed * 1.2
```

## Animation Clips
- `jump` (looping) — played while moving
- `idle` (looping) — played while stationary

## Rules
- All stats and dimensions from `EntityConfig` (slime.json)
- `xpReward` read from `cfg.stats.xpReward` — must exist in slime.json
- No scene wiring needed — `XPReward.start()` self-registers the death listener
- Adding a new mob: copy pattern, add `XPReward(cfg.stats.xpReward)` and one or more `LootDropper`s

## Known Issues
- Sprite sheet uses 1 direction row — no directional slime animation

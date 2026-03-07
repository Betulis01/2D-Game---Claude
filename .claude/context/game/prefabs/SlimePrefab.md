# SlimePrefab

**File:** `core/.../game/prefabs/mobs/SlimePrefab.java`
**Role:** Factory that constructs a slime enemy GameObject.

## Produced GameObject: "Slime"
| Component | Purpose |
|-----------|---------|
| `Transform` | Position |
| `SlimeMovement` | Random wander AI |
| `EntityMover` | direction → position |
| `AnimationDirector` | clip registry |
| `AnimationUpdater` | frame timer |
| `SpriteRenderer` | draws current frame |
| `SlimeAnimation` | moving/idle → clip switch |
| `Health` | HP from EntityConfig |
| `CombatState` | combat tracking |
| `Hurtbox` | receives damage |
| `HealthRenderer` | world-space health bar |

## Animation Clips Registered
- `jump` (looping) — played while moving
- `idle` (looping) — played while stationary

## Rules
- All stats from EntityConfig parameter
- No HealthBar on player — player has HUD (future); slimes use HealthRenderer
- Spawn position passed as parameter

## Known Issues
- Sprite sheet uses 8 rows but only row 0 is used (no directional slime animation)
- 10 slimes spawned at the same position in DeathValley — must use Tiled spawn objects

# PlayerPrefab

**File:** `core/.../game/prefabs/player/PlayerPrefab.java`
**Role:** Factory that constructs the fully configured player GameObject. Wires all player components together and registers all animation clips.

## Produced GameObject: "Player"
| Component | Purpose |
|-----------|---------|
| `Transform` | Position (set to spawn point) |
| `PlayerController` | WASD → direction |
| `EntityMover` | direction → position, bounds clamp |
| `AnimationDirector` | Clip registry |
| `AnimationUpdater` | Frame timer |
| `SpriteRenderer` | Draws current frame |
| `PlayerAnimation` | direction → clip selection (reads EntityMover) |
| `Health` | HP from EntityConfig |
| `CombatState` | Combat tracking |
| `Hurtbox` | Receives damage |
| `AttackSpawner` | Cooldown-gated attack spawning |
| `PlayerInput` | Input → attack cast |

## Animation Clips Registered
- `walk_up`, `walk_up_right`, `walk_right`, `walk_down_right`, `walk_down`, `walk_down_left`, `walk_left`, `walk_up_left` (looping)
- `idle_up`, `idle_up_right`, `idle_right`, `idle_down_right`, `idle_down`, `idle_down_left`, `idle_left`, `idle_up_left` (looping)

## Key Methods
| Method | Signature | Purpose |
|--------|-----------|---------|
| `walkClips` | `(sheet, director, frameDuration, lastFrame)` | Registers all 8 walk clips |
| `idleClips` | `(sheet, director, frameDuration)` | Registers all 8 idle clips |

## Rules
- All stats (speed, HP, hitbox size) read from EntityConfig
- `SpriteSheetSlicer` uses `cfg.sprite.frames` and `cfg.sprite.directions`
- Walk clips use `cfg.sprite.frameDuration`, idle clips use `cfg.sprite.idleFrameDuration`
- Walk end frame = `cfg.sprite.frames - 1` (passed as `lastFrame` param)
- No game logic — wiring only

## Known Issues
- Animation clip names hardcoded as strings — fragile

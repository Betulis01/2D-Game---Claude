# PlayerPrefab

**File:** `core/.../game/prefabs/player/PlayerPrefab.java`
**Role:** Factory that constructs the fully configured player GameObject. Wires all player components together and registers all animation clips.

## Produced GameObject: "Player"
| Component | Purpose |
|-----------|---------|
| `Transform` | Position (set to spawn point) |
| `PlayerController` | WASD → direction |
| `EntityMover` | direction → position, bounds clamp |
| `AnimationDirector` | clip registry |
| `AnimationUpdater` | frame timer |
| `SpriteRenderer` | draws current frame |
| `PlayerAnimation` | direction → clip + row selection |
| `Health` | HP from EntityConfig |
| `CombatState` | combat tracking |
| `Hurtbox` | receives damage |
| `AttackSpawner` | cooldown-gated attack spawning |
| `PlayerInput` | input → attack cast |

## Animation Clips Registered
- `walk_S`, `walk_SW`, `walk_W`, `walk_NW`, `walk_N`, `walk_NE`, `walk_E`, `walk_SE` (looping)
- `idle_S`, `idle_SW`, `idle_W`, `idle_NW`, `idle_N`, `idle_NE`, `idle_E`, `idle_SE` (looping)

## Rules
- All stats (speed, HP, hitbox size) read from EntityConfig parameter
- No game logic in this class — wiring only
- Spawn position passed as constructor parameter, not hardcoded here

## Known Issues
- Animation clip names hardcoded as strings — fragile
- Sprite sheet dimensions (32×32, 3 frames, 8 rows) should come from EntityConfig.sprite

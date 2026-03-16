# PlayerPrefab

**File:** `core/.../game/prefabs/player/PlayerPrefab.java`
**Role:** Factory that constructs the fully configured player GameObject. Wires all player components together and registers all animation clips.

## Produced GameObject: "Player"
| Component | Purpose |
|-----------|---------|
| `Transform` | Position (set to spawn point) |
| `PlayerController` | WASD → direction |
| `EntityMover` | direction → position, bounds clamp |
| `LayeredSpriteRenderer` | Multi-layer directional renderer |
| `CharacterAnimController` | Drives LayeredSpriteRenderer from EntityMover |
| `EquipmentLayerManager` | Adds/removes equipment visual layers |
| `Health` | HP from EntityConfig |
| `CombatState` | Combat tracking |
| `Hurtbox` | Receives damage |
| `AttackSpawner` | Cooldown-gated attack spawning |
| `PlayerInput` | Input → attack cast |

## Animation Layers
- Layer 0 (body): `DirectionalAnimSet` for IDLE + WALK from `Assets.orc_atlas`
- Layers 1+: equipment visuals added/removed by `EquipmentLayerManager`

## Rules
- All stats (speed, HP, hitbox size) read from EntityConfig
- Signature: `create(float x, float y, Assets assets)` — no Texture param
- Atlas-based animation; no `AnimationDirector`, `SpriteSheetSlicer`, or `SpriteRenderer`
- No game logic — wiring only

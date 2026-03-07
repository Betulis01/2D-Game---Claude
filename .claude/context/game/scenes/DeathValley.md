# DeathValley

**File:** `core/.../game/scenes/DeathValley.java`
**Extends:** `Scene`
**Role:** The main game level. Loads the DeathValley tile map, spawns player, enemies, and camera.

## onLoad() Sequence
1. Load `"scenes/DeathValley.tmx"` via TiledMapLoader
2. Create map GameObject with `TileMapRenderer` + `ObjectLayerRenderer`
3. Set `mapBounds` AABB from map pixel dimensions
4. Spawn player at (100, 100) via PlayerPrefab
5. Spawn 10 slimes at (200, 200) via SlimePrefab — all at same position
6. Spawn camera following player, zoom 3×, world bounds clamped to map

## Known Issues
- Player spawn position hardcoded at (100, 100) — must come from Tiled spawn object
- 10 slimes all spawned at (200, 200) — must use Tiled object layer for individual positions
- Slime count hardcoded — driven by Tiled object count instead
- Map file path hardcoded as string — acceptable for now (one map per scene class)

## What Should Drive This
Tiled ObjectLayer named `"Spawns"` with objects like:
```xml
<object type="Player" x="100" y="100"/>
<object type="Slime" x="200" y="300"/>
<object type="Slime" x="350" y="180"/>
```
Scene reads this layer and dispatches to the correct prefab by `type` property.

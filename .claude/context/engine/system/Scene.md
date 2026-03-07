# Scene

**File:** `core/.../engine/system/Scene.java`
**Type:** Abstract class
**Role:** Container for all GameObjects in a game context (level, menu, cinematic). Owns object lifetime.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `objects` | `SnapshotArray<GameObject>` | All live objects — safe to modify during iteration |
| `map` | `TiledMap` | Loaded tile map for this scene |
| `mapBounds` | `AABB` | World-space pixel bounds of the map |
| `camera` | `Camera` | Active camera for this scene |
| `game` | `Game` | Back-reference to engine |

## Lifecycle
```
onLoad() [override] → setup map, spawn prefabs
update(dt) → updates all GameObjects
render(batch) → renders all GameObjects
onUnload() [override] → cleanup
```

## Key Methods
- `addObject(GameObject)` — queues object for addition (safe mid-frame)
- `removeObject(GameObject)` — queues object for removal (safe mid-frame)
- `getObjectsByComponent(Class<T>)` — returns all objects with a given component type
- `getCamera()` / `setCamera(Camera)` — scene camera access
- `getMap()` / `getMapBounds()` — tile map access

## Rules
- Components **never** call `addObject`/`removeObject` directly — they request it through scene
- `SnapshotArray.begin()/end()` wraps all iteration to allow safe modification
- Each scene is a Java subclass — level data (spawns, map path) should come from Tiled, not hardcoded in `onLoad()`

## Known Issues
- Spawn positions and entity counts are hardcoded in `DeathValley.onLoad()` — must move to Tiled object layer

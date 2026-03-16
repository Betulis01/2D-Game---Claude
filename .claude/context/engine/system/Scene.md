# Scene

**File:** `core/.../engine/system/Scene.java`
**Type:** Abstract class
**Role:** Container for all GameObjects in a game context (level, menu, cinematic). Owns object lifetime, including a separate `overlayObjects` list that renders on top of all world objects.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `objects` | `SnapshotArray<GameObject>` | All live world objects — safe to modify during iteration |
| `overlayObjects` | `SnapshotArray<GameObject>` | Overlay objects (UI prompts, effects) — rendered after world pass |
| `map` | `TiledMap` | Loaded tile map for this scene |
| `mapBounds` | `AABB` | World-space pixel bounds of the map |
| `camera` | `Camera` | Active camera for this scene |
| `game` | `Game` | Back-reference to engine |

## Lifecycle
```
onLoad() [override] → setup map, spawn prefabs
update(dt) → updates all objects + overlayObjects
render(batch) → renders all objects (Y-sorted by layer)
renderOverlay(batch) → renders overlayObjects (no sort)
onUnload() [override] → cleanup
```

## Key Methods
- `addObject(GameObject)` — adds to `objects`, sets scene reference
- `removeObject(GameObject)` — removes from `objects`
- `addOverlayObject(GameObject)` — adds to `overlayObjects`, sets scene reference
- `removeOverlayObject(GameObject)` — removes from `overlayObjects`
- `getObjects()` — returns `objects` SnapshotArray
- `getCamera()` / `setCamera(Camera)` — scene camera access
- `getMap()` / `getMapBounds()` — tile map access

## renderComparator
Sorts world objects by sortLayer first, then by Y position descending (lower Y = higher on screen = rendered last). Checks `SimpleAnimRenderer` first, then `LayeredSpriteRenderer` if null. `RotatedSpriteRenderer` is caught by the `SimpleAnimRenderer` check automatically.

## Rules
- `overlayObjects` are rendered after all world objects — always appear on top
- `SnapshotArray.begin()/end()` wraps all iteration to allow safe modification mid-frame
- Companion overlay GameObjects (e.g. `InteractPrompt`) must be removed via `removeOverlayObject()` — `GameObject.destroy()` only calls `removeObject()`
- Each scene is a Java subclass — level data (spawns, map path) should come from Tiled, not hardcoded in `onLoad()`
- Renderer sort: check `SimpleAnimRenderer` → then `LayeredSpriteRenderer`. Never check old `SpriteRenderer`.

## Known Issues
- Spawn positions and entity counts are hardcoded in `DeathValley.onLoad()` — must move to Tiled object layer

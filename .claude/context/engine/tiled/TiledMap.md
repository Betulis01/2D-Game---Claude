# TiledMap

**File:** `core/.../engine/tiled/TiledMap.java`
**Role:** Data container for a loaded Tiled map. Produced by TiledMapLoader.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `width`, `height` | `int` | Map size in tiles |
| `tileWidth`, `tileHeight` | `int` | Tile size in pixels |
| `tileLayers` | `List<TileLayer>` | All tile layers in order |
| `objectLayers` | `List<ObjectLayer>` | All object layers |
| `tileData` | `Map<Integer, TileData>` | GID → tile definition lookup |

## Key Methods
- `getTileData(int gid)` — returns TileData for a GID, or null if not found
- `getObjectLayerByName(String)` — finds an object layer by name
- `getWidth()` → `width * tileWidth` (pixel width)
- `getHeight()` → `height * tileHeight` (pixel height)

## Rules
- GID 0 = empty tile — always check before lookup
- Pixel dimensions = tile count × tile size — always use pixel values for world-space math
- Immutable after loading — no runtime modification of tile data

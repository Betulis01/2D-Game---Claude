# TileLayer

**File:** `core/.../engine/tiled/TileLayer.java`
**Role:** Immutable data object representing one tile layer from a Tiled map.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `name` | `String` | Layer name from Tiled |
| `width`, `height` | `int` | Layer dimensions in tiles |
| `gids` | `int[][]` | `[row][col]` grid of GIDs (0 = empty) |
| `opacity` | `float` | Layer opacity (0–1) |
| `visible` | `boolean` | Whether to render this layer |

## Rules
- Immutable after construction — populated once by TiledMapLoader
- `gids[row][col]` — row 0 is the top of the map (Tiled top-left origin)
- GID 0 means no tile — skip rendering and collision checks
- `visible = false` layers are skipped by TileMapRenderer

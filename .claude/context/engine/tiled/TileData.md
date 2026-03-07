# TileData

**File:** `core/.../engine/tiled/TileData.java`
**Role:** Immutable definition of a single tile. Stores its GID, texture region, and custom properties.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `gid` | `int` | Global tile ID (unique across all tilesets) |
| `region` | `TextureRegion` | Sprite to draw for this tile |
| `properties` | `Map<String, String>` | Custom key-value metadata from Tiled |

## Usage
- Looked up by GID via `TiledMap.getTileData(gid)`
- Properties used for tile behavior (e.g., `solid`, `damage`, `biome`)
- Region passed directly to `SpriteBatch.draw()`

## Rules
- Immutable after construction
- Properties map may be empty — always null-check before reading a property

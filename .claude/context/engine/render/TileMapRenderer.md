# TileMapRenderer

**File:** `core/.../engine/render/TileMapRenderer.java`
**Extends:** `Component`
**Role:** Renders all TileLayers of a TiledMap. Performs viewport culling — only draws tiles visible on screen.

## Dependencies
- `TiledMap` — tile data and GID lookup
- `Camera` (via scene) — world→screen transform and viewBounds

## Render Logic
```
for each TileLayer (if visible):
  for each tile in camera viewBounds:
    gid = layer.gids[row][col]
    if gid == 0: skip (empty tile)
    tileData = map.getTileData(gid)
    screenX = camera.worldToScreenX(col * tileWidth)
    screenY = camera.worldToScreenY(row * tileHeight)  // Y-flip handled here
    batch.draw(tileData.region, screenX, screenY, tileWidth * zoom, tileHeight * zoom)
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `map` | `TiledMap` | Source map data |

## Rules
- Tiles draw top-left anchored (Tiled convention) — NOT center-anchored
- Tile coordinate origin: top-left of map. Y-axis is flipped for screen rendering
- Culling is mandatory — never iterate all tiles when camera shows a subset

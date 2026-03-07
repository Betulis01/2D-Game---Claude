# ObjectLayerRenderer

**File:** `core/.../engine/render/ObjectLayerRenderer.java`
**Extends:** `Component`
**Role:** Renders tile objects from Tiled ObjectLayers (decorations, props). Only renders objects with a GID (tile objects, not point/polygon objects).

## Dependencies
- `TiledMap` — object layers and GID→TileData lookup
- `Camera` (via scene) — coordinate transform

## Render Logic
```
for each ObjectLayer in map:
  for each MapObject with gid != null:
    tileData = map.getTileData(object.gid)
    // Tiled convention: object x/y = bottom-left of tile object
    screenX = camera.worldToScreenX(object.x)
    screenY = camera.worldToScreenY(object.y + object.height)  // correct for Tiled's bottom-left origin
    batch.draw(tileData.region, screenX, screenY, ...)
```

## Rules
- Tiled object `x/y` is bottom-left — the +height correction converts to top-left for rendering
- No culling currently — all objects rendered regardless of visibility
- Only tile objects (with GID) are rendered; shape/point objects are ignored here

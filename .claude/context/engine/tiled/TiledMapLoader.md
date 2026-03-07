# TiledMapLoader

**File:** `core/.../engine/tiled/TiledMapLoader.java`
**Role:** Parses Tiled TMX/TSX files (XML) and produces a TiledMap data object. Handles grid and image-collection tilesets. Caches loaded textures.

## Supported Features
- Tile layers with CSV-encoded GID data
- Object layers with properties
- External TSX tileset files
- Grid tilesets (single image sliced into regions)
- Image-collection tilesets (one image per tile)
- Multiple tilesets with firstgid offsets
- Tile properties (key-value string pairs)
- MapObject properties including `renderYOffset`

## Load Flow
```
load(path)
  → parse TMX XML
  → for each tileset: loadTileset() → populates tileData map
  → for each layer: parseTileLayer() or parseObjectLayer()
  → return TiledMap
```

## Texture Cache
- Textures keyed by file path — avoids duplicate `new Texture()` calls
- Cache is instance-level — one loader per scene load cycle

## Rules
- Path resolution is relative to the TMX file's directory
- Texture disposal is the loader's responsibility — call `dispose()` when scene unloads
- GID 0 = empty — never added to tileData map
- firstgid offset applied per tileset so global GIDs are unique across tilesets

## Known Issues
- `renderYOffset` property is parsed by string key — fragile if Tiled property name changes
- No GID range validation — invalid GIDs silently return null from `getTileData()`

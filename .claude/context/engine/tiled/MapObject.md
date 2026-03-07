# MapObject

**File:** `core/.../engine/tiled/MapObject.java`
**Role:** Represents one object from a Tiled object layer. Stores position, size, type, GID, and custom properties.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `x`, `y` | `float` | **Bottom-left** of the object (Tiled convention) |
| `width`, `height` | `float` | Object dimensions in pixels |
| `type` | `String` | Object type string from Tiled |
| `gid` | `Integer` | Tile GID if this is a tile object, else null |
| `properties` | `Map<String, String>` | Custom key-value pairs |
| `renderYOffset` | `float` | Parsed from properties — vertical render adjustment |

## Tiled Coordinate Convention
- `x/y` = bottom-left corner of the object
- Renderers must add `height` to y when converting to top-left for drawing
- World space: 1 pixel = 1 world unit

## Usage
- Tile objects (gid != null): rendered by ObjectLayerRenderer
- All objects: read by scene `onLoad()` to spawn prefabs by `type` or `properties.get("prefab")`

## Rules
- Always null-check `gid` before use — only tile objects have one
- `renderYOffset` is a special-cased property — parse as float, default 0
- Properties map may be empty — never assume a key exists

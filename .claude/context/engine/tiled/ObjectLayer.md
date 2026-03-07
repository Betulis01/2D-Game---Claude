# ObjectLayer

**File:** `core/.../engine/tiled/ObjectLayer.java`
**Role:** Immutable container for a named Tiled object layer and its MapObjects.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `name` | `String` | Layer name (used for lookup) |
| `objects` | `List<MapObject>` | All objects in this layer |

## Usage
- Retrieved by name via `TiledMap.getObjectLayerByName(name)`
- Used by scene `onLoad()` to spawn prefabs from Tiled object definitions
- Used by ObjectLayerRenderer to draw tile objects

## Rules
- Immutable after construction
- Layer names should be consistent across all maps (e.g., `"Spawns"`, `"Decorations"`)

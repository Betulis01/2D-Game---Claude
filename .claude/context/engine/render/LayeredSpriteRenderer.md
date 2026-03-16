# LayeredSpriteRenderer

**File:** `core/.../engine/render/LayeredSpriteRenderer.java`
**Extends/Type:** `Component`
**Role:** Multi-layer directional sprite renderer for the player. Layer 0 = body (always present); equipment layers start at index 1. All layers share the same state time and direction.

## Fields
| Field | Type | Purpose |
|---|---|---|
| `layers` | `List<Map<CharacterState, DirectionalAnimSet>>` | Ordered visual layers |
| `currentState` | `CharacterState` | Active animation state (IDLE/WALK/ATTACK) |
| `currentDir` | `CharacterDirection` | Current facing direction |
| `stateTime` | `float` | Shared time accumulator; resets on state change only |
| `width`, `height` | `float` | World-space sprite size |
| `sortLayer` | `int` | Render sort layer (default 2) |
| `sortYOffset` | `float` | Y offset for depth sort |

## Key Methods / Logic
| Method | Notes |
|---|---|
| `addLayer(layer)` | Appends a `Map<CharacterState, DirectionalAnimSet>` as a new visual layer |
| `removeLayersFromIndex(idx)` | Removes all layers at index and above |
| `setStateDir(state, dir)` | Updates state and direction. Resets stateTime **only** on state change (not direction change) |
| `update(dt)` | Increments stateTime |
| `render(batch)` | Iterates all layers, draws each frame at same screen pos; falls back IDLE if current state missing |

## Dependencies
- `CharacterState`, `CharacterDirection`, `DirectionalAnimSet`
- `Camera` (via `getScene().getCamera()`)

## Rules
- Layer 0 is always the body — do not remove it.
- `EquipmentLayerManager` manages layers 1+.
- Direction changes do not reset animation — state changes do.

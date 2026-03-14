# WorldItem

**File:** `core/.../game/components/items/WorldItem.java`
**Extends:** `Component`
**Role:** Self-contained world-pickup entity. Handles rendering (icon + F prompt), proximity detection, F-key pickup, and inventory insertion. No external manager needed.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `itemDef` | `ItemDefinition` | The item this entity represents |
| `playerTransform` | `Transform` | Found via scene scan in `start()` |
| `promptTexture` | `Texture` | `ui/pickup_prompt.png` loaded in `start()` |
| `showPrompt` | `boolean` | True when player is within PICKUP_RANGE |
| `input` | `InputBindings` | For polling PICKUP_ITEM (F key) |
| `PICKUP_RANGE` | `float` (constant) | 60px |

## Key Methods / Logic
- **`start()`** — scans `getScene().getObjects()` for first object with `PlayerController`; loads `ui/pickup_prompt.png` if it exists; gets InputBindings from game.
- **`update(dt)`** — computes distance to player; sets `showPrompt`; if close and F pressed → `tryPickup()`.
- **`render(batch)`** — world→screen via Camera; draws icon at 16×zoom size; draws prompt 24×24 above icon when `showPrompt`.
- **`tryPickup()`** — calls `ui.getInventory().addItem(itemDef)`; on success calls `ui.refreshBag()` then `gameObject.destroy()`.
- **`onDestroy()`** — disposes `promptTexture`.

## Dependencies
- `Camera`, `PlayerController`, `InputBindings`
- `UIManager` (via `getScene().getGame().getUI()`)
- `Inventory`, `ItemDefinition`

## Rules
- Prompt texture is loaded and owned locally per instance — disposed on `onDestroy()`
- Icon size is 16×zoom (adjusted by linter from original 32×zoom)
- Prompt size is 10×zoom (adjusted)
- Does NOT use AnimationDirector — renders static Texture directly

# WorldItem

**File:** `core/.../game/components/items/WorldItem.java`
**Extends:** `Component`
**Role:** Renders an item icon in the world. Pickup logic is handled externally by `Interactable` — this component only owns the visual and the inventory insertion.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `itemDef` | `ItemDefinition` | The item this entity represents |

## Key Methods / Logic
- **`render(batch)`** — world→screen via Camera; draws icon at 16×zoom size centered on transform.
- **`tryPickup()`** — `public`; calls `ui.getInventory().addItem(itemDef)`; on success calls `ui.refreshBag()` then `gameObject.destroy()`.

## Dependencies
- `Camera`, `UIManager`, `ItemDefinition`

## Rules
- Does NOT own proximity detection or input polling — that is delegated to `Interactable`
- Does NOT own the interact button texture — that is owned by `InteractPrompt`
- `tryPickup()` is public so `WorldItemPrefab` can pass `worldItem::tryPickup` as a method reference to `Interactable`
- Does NOT use AnimationDirector — renders static Texture directly

# Inventory

**File:** `core/.../game/items/Inventory.java`
**Extends/Type:** Plain class
**Role:** 4×4 (16-slot) backing data model for the BagPanel. Owns item placement logic.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `slots` | `ItemDefinition[16]` | Flat array, index 0–15 maps to row-major grid |

## Key Methods
- `addItem(ItemDefinition)` — inserts into first null slot; returns false if full
- `getSlot(int i)` / `setSlot(int i, ItemDefinition)` / `clearSlot(int i)` — direct slot access
- `size()` — always 16

## Rules
- Owned by UIManager, not by BagPanel (BagPanel reads it; Inventory is the source of truth)
- `WorldItem.tryPickup()` calls `addItem()` then `ui.refreshBag()` to sync the visual

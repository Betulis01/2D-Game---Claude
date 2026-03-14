# BagPanel

**File:** `core/.../game/ui/panels/BagPanel.java`
**Extends:** `UIPanel`
**Role:** 4×4 item inventory panel. Displays ItemSlot widgets backed by an Inventory model.

## Layout
- Position: `(screenW - 210, screenH - 220)`
- 4 columns × 4 rows, slot size 40px, gap 6px
- Background: `assets/ui/panels/inventory_panel.png` loaded via `loadPanelBg()` (w/h updated to PNG size)
- Starts hidden (`visible = false`)

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `itemSlots` | `ItemSlot[16]` | Grid widgets, row-major index |

## Constructor
```java
BagPanel(float screenW, float screenH, Texture slotBg, Inventory inventory)
```
Creates 16 `ItemSlot` children pre-populated from `inventory`.

## Key Methods
- `refresh(Inventory)` — syncs all slot visuals from inventory data (no add/remove of children)
- `getItemSlots()` — returns the slot array for UIManager to wire drag listeners

## Rules
- Inventory is source of truth; BagPanel only reads it via `refresh()`
- `refresh()` is safe to call during update (only calls `setItem()` on existing children)

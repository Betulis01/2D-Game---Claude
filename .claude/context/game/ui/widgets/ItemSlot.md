# ItemSlot

**File:** `core/.../game/ui/widgets/ItemSlot.java`
**Extends:** `UIWidget`
**Role:** Bag grid slot widget. Renders a slot background and item icon. Fires a DragListener on mousedown if an item is present.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `item` | `ItemDefinition` | Current item (null = empty) |
| `slotIndex` | `int` | Position in the 0–15 grid |
| `slotBg` | `Texture` | Slot background texture |
| `dragListener` | `DragListener` | Callback when drag starts |

## DragListener Interface
```java
void onDragStart(ItemDefinition item, int slotIndex)
```

## Key Methods
- `setItem(ItemDefinition)` / `getItem()` — set/get current item
- `getSlotIndex()` — returns this slot's index in the bag grid
- `setDragListener(DragListener)` — wires UIManager's drag start callback
- `render(batch, font)` — draws slotBg, then item icon (inset 2px) if non-null
- `onMouseDown(mx, my)` — fires DragListener if item != null

## Rules
- Does NOT manage inventory data — only the visual
- UIManager calls `setItem()` / `refresh()` to sync visuals to Inventory model

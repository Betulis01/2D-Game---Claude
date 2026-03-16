# EquipmentSlot

**File:** `core/.../game/ui/widgets/EquipmentSlot.java`
**Extends/Type:** `UIWidget`
**Role:** A single equipment slot widget. Accepts drag-drop of compatible items and fires `EquipListener` callbacks used by `EquipmentLayerManager` for visual layer management.

## Fields
| Field | Type | Purpose |
|---|---|---|
| `slotKey` | `String` | Identifier (e.g. "head", "chest") |
| `slotType` | `String` | Equipment type filter (e.g. "HEAD", "WEAPON") |
| `item` | `ItemDefinition` | Currently equipped item (null if empty) |
| `slotBg` | `Texture` | Background slot texture |
| `dragListener` | `DragListener` | Fired on drag start |
| `equipListener` | `EquipListener` | Fired on equip/unequip |

## Interfaces
- **`DragListener`**: `onDragStart(ItemDefinition, EquipmentSlot)` — called when drag begins
- **`EquipListener`**: `onEquip(ItemDefinition)` / `onUnequip()` — called when item changes; used by `EquipmentLayerManager`

## Key Methods
| Method | Notes |
|---|---|
| `accepts(ItemDefinition)` | True if candidate's `config.slot` matches `slotType` (case-insensitive) |
| `setItem(ItemDefinition)` | Sets item AND fires `equipListener.onEquip()` / `onUnequip()` |
| `onMouseDown(mx, my)` | Starts drag via `setItem(null)` (fires listener), passes item to dragListener |
| `getSlotKey()` | Returns slot key |

## Rules
- Always use `setItem()` to change the item — direct field assignment won't fire listeners.
- `EquipmentLayerManager` registers `EquipListener` on head/chest/legs slots only.
